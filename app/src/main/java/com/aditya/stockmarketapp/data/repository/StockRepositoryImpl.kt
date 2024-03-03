package com.aditya.stockmarketapp.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import com.aditya.stockmarketapp.data.csv.CSVParser
import com.aditya.stockmarketapp.data.local.StockDatabase
import com.aditya.stockmarketapp.data.mapper.toCompanyInfo
import com.aditya.stockmarketapp.data.mapper.toCompanyInfoEntity
import com.aditya.stockmarketapp.data.mapper.toCompanyListing
import com.aditya.stockmarketapp.data.mapper.toCompanyListingEntity
import com.aditya.stockmarketapp.data.mapper.toIntradayEntity
import com.aditya.stockmarketapp.data.mapper.toIntradayInfoList
import com.aditya.stockmarketapp.data.remote.StockApi
import com.aditya.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.aditya.stockmarketapp.domain.model.CompanyInfo
import com.aditya.stockmarketapp.domain.model.CompanyListing
import com.aditya.stockmarketapp.domain.model.IntradayInfo
import com.aditya.stockmarketapp.domain.repository.StockRepository
import com.aditya.stockmarketapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RequiresApi(Build.VERSION_CODES.O)
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfoDto>
) : StockRepository {

    private val companyListingDao = db.companyListingDao
    private val companyInfoDao = db.companyInfoDao
    private val intradayInfoDao = db.intradayInfoDao

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = companyListingDao.searchCompanyListing(query)
            emit(
                Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                companyListingDao.clearCompanyListings()
                companyListingDao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(
                    Resource.Success(
                    data = companyListingDao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getIntradayInfo(
        fetchFromRemote: Boolean,
        symbol: String
    ): Flow<Resource<List<IntradayInfo>>> {
        return flow {

            val today = LocalDate.now()
            val targetDay =
                if (today.dayOfWeek == java.time.DayOfWeek.MONDAY) today.minusDays(2) // If today is Monday, get data from Saturday (2 days ago)
                else today.minusDays(1) // Otherwise, get data from yesterday

            emit(Resource.Loading(true))
            val localIntradayInfo = intradayInfoDao.getIntradayInfo(symbol)
            if (localIntradayInfo != null) {
                val intradayList = localIntradayInfo.toIntradayInfoList().filter {
                    it.date.dayOfMonth == targetDay.dayOfMonth
                }.sortedBy {
                    it.date.hour
                }
                emit(Resource.Success(data = intradayList))
            }

            val isDbEmpty = localIntradayInfo == null
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteIntradayInfo = try {
                val response = api.getIntradayInfo(symbol)
                intradayInfoParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteIntradayInfo?.let {
                intradayInfoDao.deleteIntradayInfo(symbol)
                intradayInfoDao.insertIntradayInfo(it.toIntradayEntity(symbol))
                val newIntradayInfo =
                    intradayInfoDao.getIntradayInfo(symbol).toIntradayInfoList().filter {
                        it.date.dayOfMonth == targetDay.dayOfMonth
                    }.sortedBy {
                        it.date.hour
                    }

                emit(
                    Resource.Success(
                        data = newIntradayInfo
                    )
                )
                emit(Resource.Loading(false))
            }

        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> {

        return flow {
            try {
                emit(Resource.Loading(true))
                // Attempt to get data from the local database
                val localCompanyInfo = companyInfoDao.getCompanyInfo(symbol)

                if (localCompanyInfo != null) {
                    emit(Resource.Success(data = localCompanyInfo.toCompanyInfo()))
                } else {
                    // If local data is not available, fetch from the API
                    val result = api.getCompanyInfo(symbol)
                    result?.let {
                        companyInfoDao.deleteCompanyInfo(symbol)
                        companyInfoDao.insertCompanyInfo(result.toCompanyInfoEntity())
                        val newCompanyInfo = companyInfoDao.getCompanyInfo(symbol).toCompanyInfo()
                        emit(Resource.Success(newCompanyInfo))
                    }
                }

            } catch (e: retrofit2.HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            } catch (e: Exception){
                emit(Resource.Error("Data not available"))
            }

        }
    }


}
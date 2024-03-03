package com.aditya.stockmarketapp.domain.repository

import com.aditya.stockmarketapp.domain.model.CompanyInfo
import com.aditya.stockmarketapp.domain.model.CompanyListing
import com.aditya.stockmarketapp.domain.model.IntradayInfo
import com.aditya.stockmarketapp.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        fetchFromRemote: Boolean,
        symbol: String
    ): Flow<Resource<List<IntradayInfo>>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Flow<Resource<CompanyInfo>>
}


package com.aditya.stockmarketapp.di

import com.aditya.stockmarketapp.data.csv.CSVParser
import com.aditya.stockmarketapp.data.csv.CompanyListingParser
import com.aditya.stockmarketapp.data.csv.IntradayInfoParser
import com.aditya.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.aditya.stockmarketapp.data.repository.StockRepositoryImpl
import com.aditya.stockmarketapp.domain.model.CompanyListing
import com.aditya.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfoDto>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}
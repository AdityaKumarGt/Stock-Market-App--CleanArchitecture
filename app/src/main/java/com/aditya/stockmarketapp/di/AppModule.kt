package com.aditya.stockmarketapp.di

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.aditya.stockmarketapp.core.util.Constants.BASE_URL
import com.aditya.stockmarketapp.data.local.StockDatabase
import com.aditya.stockmarketapp.data.local.converters.IntradayInfoDtoListConverters
import com.aditya.stockmarketapp.data.remote.StockApi
import com.aditya.stockmarketapp.data.util.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stockdb.db"
        ).addTypeConverter(IntradayInfoDtoListConverters((GsonParser(Gson()))))
            .build()
    }
}
package com.aditya.stockmarketapp.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aditya.stockmarketapp.data.local.converters.IntradayInfoDtoListConverters

@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [
        CompanyListingEntity::class,
        CompanyInfoEntity::class,
        IntradayInfoEntity::class
    ],
    version = 1
)
@TypeConverters(IntradayInfoDtoListConverters::class,)
abstract class StockDatabase : RoomDatabase() {

    abstract val companyListingDao: CompanyListingDao
    abstract val companyInfoDao: CompanyInfoDao
    abstract val intradayInfoDao: IntradayInfoDao

}
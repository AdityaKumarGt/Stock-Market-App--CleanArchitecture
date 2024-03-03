package com.aditya.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IntradayInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntradayInfo(intradayInfo: IntradayInfoEntity)

    @Query("DELETE FROM IntradayInfoEntity WHERE symbol = :symbol")
    suspend fun deleteIntradayInfo(symbol: String)

    @Query("SELECT * FROM IntradayInfoEntity WHERE symbol = :symbol")
    suspend fun getIntradayInfo(symbol: String): IntradayInfoEntity
}
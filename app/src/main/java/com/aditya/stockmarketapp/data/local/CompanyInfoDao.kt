package com.aditya.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompanyInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyInfo(companyInfo: CompanyInfoEntity)

    @Query("DELETE FROM CompanyInfoEntity WHERE symbol = :symbol")
    suspend fun deleteCompanyInfo(symbol: String)

    @Query("SELECT * FROM CompanyInfoEntity WHERE symbol = :symbol")
    suspend fun getCompanyInfo(symbol: String): CompanyInfoEntity
}
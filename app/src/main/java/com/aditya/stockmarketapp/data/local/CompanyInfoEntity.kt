package com.aditya.stockmarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyInfoEntity(
    @PrimaryKey val symbol: String,
    val description: String?,
    val name: String?,
    val country: String?,
    val industry: String?,
    val address: String?,
)
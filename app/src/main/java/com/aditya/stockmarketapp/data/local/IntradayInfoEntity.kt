package com.aditya.stockmarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aditya.stockmarketapp.data.remote.dto.IntradayInfoDto

@Entity
data class IntradayInfoEntity(
    @PrimaryKey val symbol: String,
    val stockInfos: List<IntradayInfoDto>?
)
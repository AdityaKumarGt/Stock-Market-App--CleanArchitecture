package com.aditya.stockmarketapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.aditya.stockmarketapp.data.local.IntradayInfoEntity
import com.aditya.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.aditya.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun List<IntradayInfoDto>.toIntradayEntity(symbol: String): IntradayInfoEntity{
    return IntradayInfoEntity(
        symbol = symbol,
        stockInfos = this
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoEntity.toIntradayInfoList(): List<IntradayInfo>{

    return stockInfos?.map { intradayInfoDto ->
        IntradayInfo(
            date = LocalDateTime.parse(intradayInfoDto.timestamp, DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss", Locale.getDefault())),
            close = intradayInfoDto.close.toDouble()
        )
    } ?: emptyList()
}
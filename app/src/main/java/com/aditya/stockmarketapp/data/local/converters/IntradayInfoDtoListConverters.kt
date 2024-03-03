package com.aditya.stockmarketapp.data.local.converters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.aditya.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.aditya.stockmarketapp.data.util.JsonParser
import com.google.gson.reflect.TypeToken

@RequiresApi(Build.VERSION_CODES.O)
@ProvidedTypeConverter
class IntradayInfoDtoListConverters(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromJson(json: String?): List<IntradayInfoDto>? {
        return json?.let {
            jsonParser.fromJson<List<IntradayInfoDto>>(
                it,
                object : TypeToken<List<IntradayInfoDto>>() {}.type
            ) ?: emptyList()
        }
    }

    @TypeConverter
    fun toJson(intradayInfoDtos: List<IntradayInfoDto>?): String {
        return intradayInfoDtos?.let {
            jsonParser.toJson(
                it,
                object : TypeToken<List<IntradayInfoDto>>() {}.type
            ) ?: "[]"
        } ?: "[]"
    }
}
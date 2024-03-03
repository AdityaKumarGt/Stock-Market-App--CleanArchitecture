package com.aditya.stockmarketapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.aditya.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class IntradayInfoParser @Inject constructor(): CSVParser<IntradayInfoDto> {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntradayInfoDto> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null

                    IntradayInfoDto(timestamp, close)
                }
                .also {
                    csvReader.close()
                }
        }
    }
}
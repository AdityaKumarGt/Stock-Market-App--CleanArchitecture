package com.aditya.stockmarketapp.presentation.company_info

import com.aditya.stockmarketapp.domain.model.IntradayInfo

data class StockInfosState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
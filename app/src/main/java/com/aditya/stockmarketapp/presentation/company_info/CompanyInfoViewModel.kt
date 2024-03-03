package com.aditya.stockmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.stockmarketapp.domain.repository.StockRepository
import com.aditya.stockmarketapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    private lateinit var stockSymbol: String
    var companyInfoState by mutableStateOf(CompanyInfoState())
    var stockInfosState by mutableStateOf(StockInfosState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            stockSymbol = symbol
            getCompanyInfo(stockSymbol)
            getStockInfos(symbol = stockSymbol)
        }
    }


    fun refreshIntradayInfo(){
        getStockInfos(
            fetchFromRemote = true,
            symbol = stockSymbol
        )
        println("stockSymbol: ${stockSymbol}")

    }



    private fun getCompanyInfo(symbol: String) {
        viewModelScope.launch {
            repository
                .getCompanyInfo(symbol)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            companyInfoState = companyInfoState.copy(
                                company = result.data,
                                isLoading = false,
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            companyInfoState = companyInfoState.copy(
                                isLoading = false,
                                error = result.message,
                                company = null
                            )
                        }

                        is Resource.Loading -> {
                            companyInfoState = companyInfoState.copy(
                                isLoading =  result.isLoading,
                            )
                        }

                    }
                }
        }
    }

    private fun getStockInfos(
        fetchFromRemote: Boolean = false,
        symbol: String,
    ) {
        viewModelScope.launch {
            repository.getIntradayInfo(fetchFromRemote, symbol)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            stockInfosState = stockInfosState.copy(
                                stockInfos = result.data ?: emptyList(),
                                isLoading = false,
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            stockInfosState = stockInfosState.copy(
                                isLoading = false,
                                error = result.message,
                                stockInfos = emptyList()
                            )
                        }

                        is Resource.Loading -> {
                            stockInfosState = stockInfosState.copy(
                                isLoading =  result.isLoading,
                            )
                        }
                    }
                }
        }
    }
}
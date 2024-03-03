package com.aditya.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.stockmarketapp.domain.repository.StockRepository
import com.aditya.stockmarketapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repository: StockRepository
): ViewModel() {

    var state by mutableStateOf(CompanyListingState())

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingsEvent){
        when(event){
            CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }

            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)  //whenever we are typing something,
                searchJob?.cancel()   // it cancels any type of searching going on in the background,
                searchJob = viewModelScope.launch {// until we stops typing for 0.5 seconds
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

   private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){
        viewModelScope.launch {
            repository
                .getCompanyListings(fetchFromRemote,query)
                .collect{result ->
                    when(result){
                        is Resource.Success -> {
                            result.data?.let {listings ->
                                state = state.copy(
                                    companies = listings
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message ?: "An unexpected error occured"
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }

                    }
                }
        }

    }
}
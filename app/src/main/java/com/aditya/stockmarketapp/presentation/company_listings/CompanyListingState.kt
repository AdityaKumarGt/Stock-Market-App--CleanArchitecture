package com.aditya.stockmarketapp.presentation.company_listings

import com.aditya.stockmarketapp.domain.model.CompanyListing

data class CompanyListingState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)

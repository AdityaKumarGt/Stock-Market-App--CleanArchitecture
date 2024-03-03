package com.aditya.stockmarketapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyInfoDto(
    @SerializedName("Symbol") val symbol: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("Industry") val industry: String?,
    @SerializedName("Address") val address: String?
    )

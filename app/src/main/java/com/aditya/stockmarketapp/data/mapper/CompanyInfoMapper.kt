package com.aditya.stockmarketapp.data.mapper

import com.aditya.stockmarketapp.data.local.CompanyInfoEntity
import com.aditya.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.aditya.stockmarketapp.domain.model.CompanyInfo

fun CompanyInfoDto.toCompanyInfoEntity(): CompanyInfoEntity {
    return CompanyInfoEntity(
        symbol = symbol ?: "",
        description = description,
        name = name,
        country = country,
        industry = industry,
        address = address
    )
}


fun CompanyInfoEntity.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol,
        description = description,
        name = name,
        country = country,
        industry = industry,
        address = address
    )
}

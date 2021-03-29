package com.zedelivery.desafioze.domain.dtos.response

import com.zedelivery.desafioze.domain.dtos.AddressDto

data class PartnerDto(
    val id: String,
    val address: AddressDto,
    val coverageArea: CoverageArea,
    val document: String,
    val ownerName: String,
    val tradingName: String
)
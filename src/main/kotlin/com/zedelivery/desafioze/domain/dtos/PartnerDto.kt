package com.zedelivery.desafioze.domain.dtos

data class PartnerDto(
    val id: String,
    val address: AddressDto,
    val coverageArea: CoverageAreaDto,
    val document: String,
    val ownerName: String,
    val tradingName: String
)
package com.zedelivery.desafioze.domain.dtos

data class Pdv(
    val address: AddressDto,
    val coverageArea: CoverageAreaDto,
    val document: String,
    val id: String,
    val ownerName: String,
    val tradingName: String
)
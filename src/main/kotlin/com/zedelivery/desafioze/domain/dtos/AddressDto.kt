package com.zedelivery.desafioze.domain.dtos

data class AddressDto(
    val coordinates: List<Double>,
    val type: String
)
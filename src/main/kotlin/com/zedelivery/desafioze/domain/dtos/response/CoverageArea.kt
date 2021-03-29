package com.zedelivery.desafioze.domain.dtos.response

import org.springframework.data.geo.Point

data class CoverageArea(
    val coordinates: MutableList<Point>,
    var type: String
)
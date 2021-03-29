package com.zedelivery.desafioze.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "partners")
data class Partner(
    @Id
    val id: String,
    @GeoSpatialIndexed(name = "address", type = GeoSpatialIndexType.GEO_2DSPHERE)
    var address: GeoJsonPoint,
    var coverageArea: GeoJsonMultiPolygon,
    val document: String,
    val ownerName: String,
    val tradingName: String
)
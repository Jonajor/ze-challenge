package com.zedelivery.desafioze.domain.factories

import com.zedelivery.desafioze.domain.dtos.AddressDto
import com.zedelivery.desafioze.domain.dtos.Pdv
import com.zedelivery.desafioze.domain.dtos.response.CoverageArea
import com.zedelivery.desafioze.domain.dtos.response.PartnerDto
import com.zedelivery.desafioze.domain.entities.Partner
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.stereotype.Service


@Service
class PartnerFactory {

    fun convertEntityToDto(partner: Partner): PartnerDto {

        val address = AddressDto(
            type = partner.address.type,
            coordinates = partner.address.coordinates
        )

        val area = CoverageArea(
            type = partner.coverageArea.type,
            coordinates = partner.coverageArea.coordinates[0].coordinates[0].coordinates
        )

        return PartnerDto(
            id = partner.id,
            address = address,
            coverageArea = area,
            document = partner.document,
            ownerName = partner.ownerName,
            tradingName = partner.tradingName
        )
    }
    
    fun convertPdvToEntity(pdv: Pdv): Partner {

        val address = GeoJsonPoint(
            pdv.address.coordinates[0],
            pdv.address.coordinates[1]
        )

        val area = GeoJsonMultiPolygon(pdv.coverageArea.coordinates as MutableList<GeoJsonPolygon>)

        return Partner(
            id = pdv.id,
            address = address,
            coverageArea = area,
            document = pdv.document,
            ownerName = pdv.ownerName,
            tradingName = pdv.tradingName
        )

    }
    
}
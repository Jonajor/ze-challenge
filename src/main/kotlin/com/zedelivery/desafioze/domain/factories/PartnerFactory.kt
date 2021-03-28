package com.zedelivery.desafioze.domain.factories

import com.zedelivery.desafioze.domain.dtos.PartnerDto
import com.zedelivery.desafioze.domain.dtos.Pdv
import com.zedelivery.desafioze.domain.entities.Partner
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.stereotype.Service


@Service
class PartnerFactory {

    fun convertDtoToEntity(partnerDto: PartnerDto): Partner {
        val address = GeoJsonPoint(
            partnerDto.address.coordinates[0],
            partnerDto.address.coordinates[1]
        )

        val area = GeoJsonMultiPolygon(partnerDto.coverageArea.coordinates as MutableList<GeoJsonPolygon>)

        return Partner(
            id = partnerDto.id,
            address = address,
            coverageArea = area,
            document = partnerDto.document,
            ownerName = partnerDto.ownerName,
            tradingName = partnerDto.tradingName
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
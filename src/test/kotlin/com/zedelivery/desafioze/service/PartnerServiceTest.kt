package com.zedelivery.desafioze.service

import com.google.gson.Gson
import com.zedelivery.desafioze.domain.dtos.AddressDto
import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.Pdv
import com.zedelivery.desafioze.domain.dtos.response.CoverageArea
import com.zedelivery.desafioze.domain.dtos.response.PartnerDto
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.factories.PartnerFactory
import com.zedelivery.desafioze.domain.repositories.PartnerRepository
import com.zedelivery.desafioze.domain.services.PartnerService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.BufferedReader
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

import org.hamcrest.Factory
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PartnerServiceTest {

    @InjectMocks
    lateinit var partnerService: PartnerService

    @Mock
    lateinit var partnerRepository: PartnerRepository

    @Mock
    lateinit var partnerFactory: PartnerFactory

    @Test
    fun `Deve verificar se o id existe`(){
        Mockito.`when`(partnerRepository.existsById("1")).thenReturn(true)
        var result = partnerService.existById("1")
        assertEquals(result, true)
    }

    @Test
    fun `Deve retorna a consulta no banco com parceiro preenchido`(){
        val gson = Gson()
        val bufferedReader: BufferedReader = File ("src/test/resources/partner.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val dataContract = gson.fromJson(inputString, DataContract::class.java)
        var partner = factoryMethod(dataContract.pdvs[0])
        var dto = factoryMethodToDto()
        Mockito.`when`(partnerFactory.convertEntityToDto(partner)).thenReturn(dto)
        Mockito.`when`(partnerRepository.findById("1")).thenReturn(Optional.of(partner))
        var result = partnerService.findById("1")
        assertNotNull(result)
        assertEquals(result.id, "1")
    }

    @Test
    fun `Deve buscar parceiro por localizacao e distancia`(){
        val addressDtoUm = GeoJsonPoint(Point(51.4678685, -0.0860632))
        val addressDtoDois = GeoJsonPoint(Point(51.461512, -0.078988))
        val addressDtoTres = GeoJsonPoint(Point(51.4651705, -0.0895804))
        val addressDtoQuatro = GeoJsonPoint(Point(51.461744, -0.070394))
        val addressDtoCinco = GeoJsonPoint(Point(51.460463, -0.07513))
        val partnerList = ArrayList<Partner>()
        val coverageArea = GeoJsonMultiPolygon(mutableListOf(GeoJsonPolygon(mutableListOf(Point(1.0, 1.0),Point(1.0, 1.0)))))
        partnerList.add(Partner("1", addressDtoUm, coverageArea , "teste1", "teste1", "teste1"))
        partnerList.add(Partner("2", addressDtoDois, coverageArea, "teste2", "teste2", "teste2"))
        partnerList.add(Partner("3", addressDtoTres, coverageArea, "teste3", "teste3", "teste3"))
        partnerList.add(Partner("4", addressDtoQuatro, coverageArea, "teste4", "teste4", "teste4"))
        partnerList.add(Partner("5", addressDtoCinco, coverageArea, "teste5", "teste5", "teste5"))

        val partnerList2 = ArrayList<Partner>()
        partnerList2.add(Partner("1", addressDtoUm, coverageArea , "teste1", "teste1", "teste1"))
        partnerList2.add(Partner("2", addressDtoDois, coverageArea, "teste2", "teste2", "teste2"))
        partnerList2.add(Partner("3", addressDtoTres, coverageArea, "teste3", "teste3", "teste3"))

        Mockito.`when`(partnerRepository.findByAddressNear(Point(51.4634836, -0.0841914),
            Distance(1.0, Metrics.KILOMETERS))).thenReturn(partnerList2)
        val result = partnerService.getLocationNear(51.4634836, -0.0841914, 1.0)
        assertNotNull(result)
    }

    @Factory
    fun factoryMethodToDto(): PartnerDto {

        val addressDto = AddressDto(listOf(1.0, 1.0), "teste")
        val coverageArea = CoverageArea(mutableListOf(Point(1.0, 1.0)), "MultiPolygin")
        return PartnerDto("1", addressDto, coverageArea, "teste", "teste", "teste")
    }

    @Factory
    fun factoryMethod(pdv: Pdv): Partner {

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
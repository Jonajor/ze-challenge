package com.zedelivery.desafioze.controller

import com.zedelivery.desafioze.domain.dtos.AddressDto
import com.zedelivery.desafioze.domain.dtos.CoverageAreaDto
import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.Pdv
import com.zedelivery.desafioze.domain.dtos.response.CoverageArea
import com.zedelivery.desafioze.domain.dtos.response.PartnerDto
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.services.PartnerService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.http.HttpStatus
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@RunWith(MockitoJUnitRunner::class)
@WebMvcTest
class PartnerControllerTest {

    @InjectMocks
    lateinit var partnerController: PartnerController
    @Mock
    lateinit var partnerService: PartnerService


    @Test
    @Throws(Exception::class)
    fun `deve buscar o parceiro por Id e retornar codigo 200`() {
        val addressDto = AddressDto(listOf(1.0, 1.0), "teste")
        val coverageArea = CoverageArea(mutableListOf(Point(1.0, 1.0)), "MultiPolygin")
        val partnerDto = PartnerDto("1", addressDto, coverageArea, "teste", "teste", "teste")

        Mockito.`when`(partnerService.existById("1")).thenReturn(true)
        Mockito.`when`(partnerService.findById("1")).thenReturn(partnerDto)
        val result = partnerController.findById("1")
        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
    }

    @Test
    @Throws(Exception::class)
    fun `Deve buscar o parceiro por id inexistente e retornar 404`() {
        val addressDto = AddressDto(listOf(1.0, 1.0), "teste")
        val coverageArea = CoverageArea(mutableListOf(Point(1.0, 1.0)), "MultiPolygin")
        PartnerDto("1", addressDto, coverageArea, "teste", "teste", "teste")

        Mockito.`when`(partnerService.existById("2")).thenReturn(false)
        val result = partnerController.findById("2")
        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.NOT_FOUND)
        assertEquals(result.body, "Id not found: 2")
    }

    @Test
    @Throws(Exception::class)
    fun `deve buscar o parceiro localizacao e retornar codigo 200`() {
        val addressDto = AddressDto(listOf(51.4678685, -0.0860632), "teste")
        val coverageArea = CoverageArea(mutableListOf(Point(1.0, 1.0)), "MultiPolygin")
        val partnerList = ArrayList<PartnerDto>()
        partnerList.add(PartnerDto("1", addressDto, coverageArea, "teste", "teste", "teste"))

        Mockito.`when`(partnerService.getLocationNear(51.4634836, -0.0841914, 1.0)).thenReturn(partnerList)
        val result = partnerController.getLocations(51.4634836, -0.0841914, 1.0)
        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
    }

}
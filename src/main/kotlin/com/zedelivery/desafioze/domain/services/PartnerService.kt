package com.zedelivery.desafioze.domain.services

import com.google.gson.Gson
import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.response.PartnerDto
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.factories.PartnerFactory
import com.zedelivery.desafioze.domain.repositories.PartnerRepository
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.util.*


@Service
class PartnerService(private val partnerRepository: PartnerRepository,
                     private val partnerFactory: PartnerFactory) {

    fun existById(id: String): Boolean{
        return partnerRepository.existsById(id)
    }

    fun findById(id: String): PartnerDto {
        val partner = partnerRepository.findById(id).get()
        return partnerFactory.convertEntityToDto(partner)
    }

    fun getLocationNear(latitude: Double, longitude: Double, distance: Double): List<PartnerDto> {
        val partner = partnerRepository.findByAddressNear(
            Point(latitude, longitude),
            Distance(distance, Metrics.KILOMETERS)
        )
        return convertToDto(partner)
    }

    private fun convertToDto(partner: List<Partner>): ArrayList<PartnerDto> {
        val partnerList = ArrayList<PartnerDto>()
        partner.forEach {
            partnerList.add(partnerFactory.convertEntityToDto(it))
        }

        return partnerList
    }

    @EventListener(ContextRefreshedEvent::class)
    private fun createFromFile() {
        val gson = Gson()
        val bufferedReader: BufferedReader = File ("src/main/resources/pdvs.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val dataContract = gson.fromJson(inputString, DataContract::class.java)
        val partnerList = ArrayList<Partner>()
        dataContract.pdvs.forEach {
            partnerList.add(partnerFactory.convertPdvToEntity(it))
        }
        partnerRepository.saveAll(partnerList)
    }

}
package com.zedelivery.desafioze.domain.services

import com.google.gson.Gson
import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.PartnerDto
import com.zedelivery.desafioze.domain.dtos.Pdv
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.factories.PartnerFactory
import com.zedelivery.desafioze.domain.repositories.PartnerRepository
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.io.BufferedReader
import java.io.File
import java.util.*


@Service
class PartnerService(private val partnerRepository: PartnerRepository,
                     private val partnerFactory: PartnerFactory) {

    fun findById(id: String): Mono<Partner> {
        return partnerRepository.findById(id)
    }

    fun createPartner(partnerDto: PartnerDto): Mono<Partner> {
        return partnerRepository.save(partnerFactory.convertDtoToEntity(partnerDto))
    }

    fun createFromFile(): Flux<Partner>{
        var gson = Gson()
        val bufferedReader: BufferedReader = File ("src/main/resources/pdvs.json").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        var dataContract = gson.fromJson(inputString, DataContract::class.java)
        return createAll(dataContract)
    }

    fun createAll(dataContract: DataContract): Flux<Partner> {
        val partnerList = ArrayList<Partner>()
        dataContract.pdvs.forEach {
            partnerList.add(partnerFactory.convertPdvToEntity(it))
        }
        return partnerRepository.saveAll(partnerList)
    }

    fun getLocationNear(latitude: Double, longitude: Double, distance: Double): Flux<Partner> {
        return partnerRepository.findByAddressNear(
            Point(latitude, longitude),
            Distance(distance, Metrics.KILOMETERS)
        )
    }

}
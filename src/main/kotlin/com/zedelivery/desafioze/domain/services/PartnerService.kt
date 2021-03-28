package com.zedelivery.desafioze.domain.services

import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.PartnerDto
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.factories.PartnerFactory
import com.zedelivery.desafioze.domain.repositories.PartnerRepository
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


@Service
class PartnerService(private val partnerRepository: PartnerRepository,
                     private val partnerFactory: PartnerFactory) {
    fun getAll(): Flux<Partner> = partnerRepository.findAll()

    fun findById(id: String): Mono<Partner> {
        return partnerRepository.findById(id)
    }

    fun createPartner(partnerDto: PartnerDto): Mono<Partner> {
        return partnerRepository.save(partnerFactory.convertDtoToEntity(partnerDto))
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
package com.zedelivery.desafioze.controller

import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.PartnerDto
import com.zedelivery.desafioze.domain.dtos.Pdv
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.services.PartnerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid


@RestController
@RequestMapping("/partners")
class PartnerController(private val partnerService: PartnerService){

    //TODO: Ajustar tipo de retorno conforme contrato do git
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): Mono<ResponseEntity<Partner>> {
        return partnerService.findById(id)
            .map { getPartner -> ResponseEntity.ok(getPartner) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @GetMapping("/location")
    fun getLocations(
        @RequestParam latitude: Double, @RequestParam longitude: Double,
        @RequestParam distance: Double): Flux<Partner> {
        return partnerService.getLocationNear(latitude, longitude, distance)
    }

    @PostMapping
    fun save(@Valid @RequestBody partnerDto: PartnerDto): ResponseEntity<Mono<Partner>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.createPartner(partnerDto))
    }

    @PostMapping("/batch")
    fun saveAll(@RequestBody dataContract: DataContract): ResponseEntity<Flux<Partner>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.createAll(dataContract))
    }

    @PostMapping("/batch/fromFile")
    fun saveAllFromFile(): ResponseEntity<Flux<Partner>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.createFromFile())
    }

}
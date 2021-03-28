package com.zedelivery.desafioze.controller

import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.PartnerDto
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.services.PartnerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid


@RestController
@RequestMapping("/partners")
class PartnerController(private val partnerService: PartnerService){

    @GetMapping
    fun getAllTweets(): Flux<Partner> = partnerService.getAll()

    //TODO: Ajustar tipo de retorno conforme contrato do git
    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): Mono<ResponseEntity<Partner>> {
        return partnerService.findById(id)
            .map { savedPartner -> ResponseEntity.ok(savedPartner) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @GetMapping("/location")
    fun getLocations(
        @RequestParam latitude: Double, @RequestParam longitude: Double,
        @RequestParam distance: Double): Flux<Partner> {
        return partnerService.getLocationNear(latitude, longitude, distance)
    }

    @PostMapping
    fun save(@Valid @RequestBody partnerDto: PartnerDto): Mono<Partner>  {
        return partnerService.createPartner(partnerDto)
    }


    @PostMapping("/batch")
    fun saveteste(@RequestBody dataContract: DataContract): Flux<Partner> {
        return partnerService.createAll(dataContract)
    }

}
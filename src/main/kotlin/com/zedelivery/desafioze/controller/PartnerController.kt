package com.zedelivery.desafioze.controller

import com.zedelivery.desafioze.domain.dtos.DataContract
import com.zedelivery.desafioze.domain.dtos.response.PartnerDto
import com.zedelivery.desafioze.domain.entities.Partner
import com.zedelivery.desafioze.domain.services.PartnerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/partners")
class PartnerController(private val partnerService: PartnerService){

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): ResponseEntity<Any>? {
        return takeIf { it.partnerService.existById(id) }
            ?. let { ResponseEntity.ok().body(partnerService.findById(id)) }
            ?: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found: $id")
    }

    @GetMapping("/location")
    fun getLocations(
        @RequestParam latitude: Double, @RequestParam longitude: Double,
        @RequestParam distance: Double): ResponseEntity<List<PartnerDto>> {
        return ResponseEntity.status(HttpStatus.OK).body(partnerService.getLocationNear(latitude, longitude, distance))
    }

    @PostMapping("/batch")
    fun saveAll(@RequestBody dataContract: DataContract): ResponseEntity<MutableIterable<Partner>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.createAll(dataContract))
    }
}
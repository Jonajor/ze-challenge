package com.zedelivery.desafioze.domain.repositories

import com.zedelivery.desafioze.domain.entities.Partner
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


interface PartnerRepository : CrudRepository<Partner, String> {
  fun findByAddressNear(point: Point, distance: Distance): List<Partner>
}
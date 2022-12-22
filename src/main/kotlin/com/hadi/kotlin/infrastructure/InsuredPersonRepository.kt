package com.hadi.kotlin.infrastructure

import com.hadi.kotlin.domain.InsuredPerson
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface InsuredPersonRepository : JpaRepository<InsuredPerson, Long> {
  fun findByUuid(uuid: UUID) : InsuredPerson
  fun deleteByPolicy(id: Long)
}

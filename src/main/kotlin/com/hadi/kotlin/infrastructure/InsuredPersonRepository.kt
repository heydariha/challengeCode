package com.hadi.kotlin.infrastructure

import com.hadi.kotlin.domain.InsuredPerson
import org.springframework.data.jpa.repository.JpaRepository


interface InsuredPersonRepository : JpaRepository<InsuredPerson, Long> {
}

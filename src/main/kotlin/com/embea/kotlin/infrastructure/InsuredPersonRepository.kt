package com.embea.kotlin.infrastructure

import com.embea.kotlin.domain.InsuredPerson
import org.springframework.data.jpa.repository.JpaRepository


interface InsuredPersonRepository : JpaRepository<InsuredPerson, Long> {
}

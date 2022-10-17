package com.hadi.kotlin.infrastructure

import com.hadi.kotlin.domain.Policy
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*


interface PolicyRepository : JpaRepository<Policy, Long> {
    fun findByUuid(uuid: UUID): Policy?
    fun findPolicyByUuidAndStartDate(policyId: UUID, requestDate: LocalDate): Policy?
}
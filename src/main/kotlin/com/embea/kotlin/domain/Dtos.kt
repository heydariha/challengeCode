package com.embea.kotlin.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


data class FindPolicyDto (
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val requestDate: LocalDate,
    val policyId: UUID,
)
data class PolicyDto (
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val startDate: LocalDate,
    val insuredPersons: List<InsuredPersonDto>
)

data class InsuredPersonDto (
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal
)

data class IntegratedPolicyDto (
    val policyId: UUID,
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val startDate: LocalDate,
    val insuredPersons: List<InsuredPersonDto>,
    val totalPremium: BigDecimal
)

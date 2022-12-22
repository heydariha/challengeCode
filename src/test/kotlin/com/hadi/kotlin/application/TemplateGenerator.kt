package com.hadi.kotlin.application

import com.hadi.kotlin.domain.InsuredPersonDto
import com.hadi.kotlin.domain.IntegratedPolicyDto
import com.hadi.kotlin.domain.PolicyDto
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

fun createIntegratedPolicyDto(
    policyId: UUID,
    listOfInsuredPersons: List<InsuredPersonDto> = listOf(createInsuredPersonDtoTemplate()),
    startDate: LocalDate = LocalDate.now()
): IntegratedPolicyDto = IntegratedPolicyDto(
    policyId = policyId,
    startDate = startDate,
    insuredPersons = listOfInsuredPersons,
    totalPremium = BigDecimal.ONE
)


fun createPolicyDtoTemplate(startDate: LocalDate = LocalDate.now()): PolicyDto = PolicyDto(
    startDate = startDate,
    insuredPersons = listOf(
        createInsuredPersonDtoTemplate(),
        createInsuredPersonDtoTemplate(firstName = "Daniel", secondName = "Schwarz", premium = BigDecimal.ONE)
    )
)

fun createInsuredPersonDtoTemplate(
    firstName: String = "Hadi",
    secondName: String = "Heydari",
    premium: BigDecimal = BigDecimal.TEN
): InsuredPersonDto = InsuredPersonDto(
    uuid = null,
    firstName = firstName,
    secondName = secondName,
    premium = premium
)

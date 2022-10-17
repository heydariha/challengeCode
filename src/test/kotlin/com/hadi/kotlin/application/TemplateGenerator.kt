package com.hadi.kotlin.application

import com.hadi.kotlin.domain.InsuredPersonDto
import com.hadi.kotlin.domain.IntegratedPolicyDto
import com.hadi.kotlin.domain.PolicyDto
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

fun createIntegratedPolicyDto(
    policyId: UUID,
    listOfInsuredPersons: List<InsuredPersonDto> = listOf(createInsuredPersonDtoTemplate())
): IntegratedPolicyDto = IntegratedPolicyDto(
    policyId = policyId,
    startDate = LocalDate.now(),
    insuredPersons = listOfInsuredPersons,
    totalPremium = BigDecimal.ONE
)


fun createPolicyDtoTemplate(): PolicyDto = PolicyDto(
    startDate = LocalDate.now(),
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
    firstName = firstName,
    secondName = secondName,
    premium = premium
)
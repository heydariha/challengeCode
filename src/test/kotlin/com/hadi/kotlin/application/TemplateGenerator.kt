package com.hadi.kotlin.application

import com.hadi.kotlin.domain.InsuredPerson
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
    totalPremium = 1.00
)


fun createPolicyDtoTemplate(startDate: LocalDate = LocalDate.now()): PolicyDto = PolicyDto(
    startDate = startDate,
    insuredPersons = listOf(
        createInsuredPersonDtoTemplate(),
        createInsuredPersonDtoTemplate(firstName = "Daniel", secondName = "Schwarz", premium = 1.00)
    )
)

fun createInsuredPersonDtoTemplate(
    uuid: UUID? = null,
    firstName: String = "Hadi",
    secondName: String = "Heydari",
    premium: Double = 10.00
): InsuredPersonDto = InsuredPersonDto(
    uuid = uuid,
    firstName = firstName,
    secondName = secondName,
    premium = premium
)

fun createInsuredPersonTemplate(
  firstName: String = "Hadi",
  secondName: String = "Heydari",
  premium: Double = 10.00
): InsuredPerson = InsuredPerson(
  id = 0,
  uuid = UUID.randomUUID(),
  firstName = firstName,
  secondName = secondName,
  premium = premium,
  policy = null
)

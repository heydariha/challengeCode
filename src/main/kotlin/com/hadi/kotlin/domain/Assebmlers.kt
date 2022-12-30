package com.hadi.kotlin.domain

import java.util.*

fun assemblePolicy(policyDto: PolicyDto): Policy = Policy(
  id = 0,
  startDate = policyDto.startDate,
  insuredPersons = policyDto.insuredPersons.map { assembleInsuredPersons(it) }
)

fun assembleInsuredPersons(insuredPersonDto: InsuredPersonDto): InsuredPerson {
  return InsuredPerson(
    id = 0,
    uuid = when (insuredPersonDto.uuid) {
      null -> UUID.randomUUID()
      else -> insuredPersonDto.uuid
    },
    firstName = insuredPersonDto.firstName,
    secondName = insuredPersonDto.secondName,
    premium = insuredPersonDto.premium,
    policy = null
  )
}

fun assemblePolicyResponseDto(policy: Policy): IntegratedPolicyDto = IntegratedPolicyDto(
  policyId = policy.uuid,
  startDate = policy.startDate,
  insuredPersons = policy.insuredPersons.map { assembleInsuredPersonDto(it) },
  totalPremium = policy.insuredPersons.sumOf { it.premium }
)

fun assembleInsuredPersonDto(insuredPerson: InsuredPerson): InsuredPersonDto = InsuredPersonDto(
  uuid = insuredPerson.uuid,
  firstName = insuredPerson.firstName,
  secondName = insuredPerson.secondName,
  premium = insuredPerson.premium
)

package com.hadi.kotlin.domain

fun assembleUpdatedPolicy(policy: Policy, integratedPolicyDto: IntegratedPolicyDto): Policy {
    return policy.apply {
        uuid = integratedPolicyDto.policyId
        startDate = integratedPolicyDto.startDate
        insuredPersons = integratedPolicyDto.insuredPersons.map { assembleInsuredPersons(it) }
    }
}

fun assemblePolicy(policyDto: PolicyDto): Policy = Policy(
    id = 0,
    startDate = policyDto.startDate,
    insuredPersons = policyDto.insuredPersons.map { assembleInsuredPersons(it) }
)

fun assembleInsuredPersons(insuredPersonDto: InsuredPersonDto): InsuredPerson = InsuredPerson(
    id = 0,
    firstName = insuredPersonDto.firstName,
    secondName = insuredPersonDto.secondName,
    premium = insuredPersonDto.premium,
    policy = null
)

fun assemblePolicyResponseDto(policy: Policy): IntegratedPolicyDto = IntegratedPolicyDto(
    policyId = policy.uuid,
    startDate = policy.startDate,
    insuredPersons = policy.insuredPersons.map { assembleInsuredPersonDto(it) },
    totalPremium = policy.insuredPersons.sumOf { it.premium }
)

fun assembleInsuredPersonDto(insuredPerson: InsuredPerson): InsuredPersonDto = InsuredPersonDto(
    firstName = insuredPerson.firstName,
    secondName = insuredPerson.secondName,
    premium = insuredPerson.premium
)
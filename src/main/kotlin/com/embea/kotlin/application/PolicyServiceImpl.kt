package com.embea.kotlin.application

import com.embea.kotlin.config.NotFoundException
import com.embea.kotlin.domain.*
import com.embea.kotlin.infrastructure.InsuredPersonRepository
import com.embea.kotlin.infrastructure.PolicyRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class PolicyServiceImpl(val policyRepository: PolicyRepository, val InsuredPersonRepository: InsuredPersonRepository) : PolicyService {

    override fun getPolicy(findPolicyDto: FindPolicyDto): IntegratedPolicyDto? {
        val policy = policyRepository.findPolicyByUuidAndStartDate(findPolicyDto.policyId, findPolicyDto.requestDate)
        return policy?.let { assemblePolicyResponseDto(it) }
    }

    override fun createPolicy(policyDto: PolicyDto): IntegratedPolicyDto {
        val policy = assemblePolicy(policyDto)
        policy.addToInsuredPerson(policy.insuredPersons)
        val newPolicy = policyRepository.save(policy)
        return assemblePolicyResponseDto(newPolicy)
    }

    override fun updatePolicy(integratedPolicyDto: IntegratedPolicyDto): IntegratedPolicyDto {
        val policy = assembleUpdatedPolicy(getPolicy(integratedPolicyDto.policyId)!!, integratedPolicyDto)
        policy.addToInsuredPerson(policy.insuredPersons)
        val kir = policyRepository.save(policy)
        return assemblePolicyResponseDto(kir)
    }

    private fun getPolicy(policyId: UUID): Policy? {
        return try {
            policyRepository.findByUuid(policyId)!!
        } catch (e: Exception) {
            throw NotFoundException(
                message = "Policy not found",
                detailMessage = "Policy with Id : $policyId not found"
            )
        }
    }
}
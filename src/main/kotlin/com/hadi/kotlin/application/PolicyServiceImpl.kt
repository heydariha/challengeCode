package com.hadi.kotlin.application

import com.hadi.kotlin.config.NotFoundException
import com.hadi.kotlin.domain.*
import com.hadi.kotlin.infrastructure.PolicyRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class PolicyServiceImpl(val policyRepository: PolicyRepository) :
    PolicyService {

    override fun getPolicy(findPolicyDto: FindPolicyDto): IntegratedPolicyDto? {
        val policy = policyRepository.findPolicyByUuidAndStartDate(findPolicyDto.policyId, findPolicyDto.requestDate)
        return policy?.let { assemblePolicyResponseDto(it) }
    }

    override fun getPolicy(): List<IntegratedPolicyDto> {
      val policy = policyRepository.findAll()
      return policy.map { assemblePolicyResponseDto(it) }
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

package com.hadi.kotlin.application.impl

import com.hadi.kotlin.application.api.InsuredPersonService
import com.hadi.kotlin.application.api.PolicyService
import com.hadi.kotlin.config.NotFoundException
import com.hadi.kotlin.domain.*
import com.hadi.kotlin.infrastructure.InsuredPersonRepository
import com.hadi.kotlin.infrastructure.PolicyRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional

@Component
class PolicyServiceImpl(val policyRepository: PolicyRepository,
                        val insuredPersonService: InsuredPersonService) :
  PolicyService {

  override fun getPolicy(findPolicyDto: FindPolicyDto): IntegratedPolicyDto? {
    val startDate = when (findPolicyDto.requestDate) {
      null -> LocalDate.now()
      else -> findPolicyDto.requestDate
    }
    val policy = policyRepository.findPolicyByUuidAndStartDate(findPolicyDto.policyId, startDate)
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
    val existingPolicy = getPolicy(integratedPolicyDto.policyId)
    val policy = assembleUpdatedPolicy(existingPolicy!!, integratedPolicyDto)
//    policy.insuredPersons = listOf()
//    policyRepository.save(policy)

    val insuredPersons = insuredPersonService.assembleInsuredPersonForUpdate(integratedPolicyDto.insuredPersons)
    policy.addToInsuredPerson(insuredPersons)
    val result = policyRepository.save(policy)
    return assemblePolicyResponseDto(result)
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

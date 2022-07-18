package com.embea.kotlin.application

import com.embea.kotlin.domain.FindPolicyDto
import com.embea.kotlin.domain.IntegratedPolicyDto
import com.embea.kotlin.infrastructure.PolicyRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
internal class PolicyServiceImplTest {

    @Autowired
    lateinit var policyService: PolicyService

    @Autowired
    lateinit var policyRepository: PolicyRepository

    lateinit var basePolicy: IntegratedPolicyDto


    private fun createPolicy() {
        val policies = createPolicyDtoTemplate()
        basePolicy = policyService.createPolicy(policies)
    }

    @Test
    fun `create Policy test should create two new policies`() {
        //GIVEN
        createPolicy()

        //WHEN
        val newPolices = policyRepository.findAll()

        //THEN
        assertTrue(newPolices.find { it.uuid == basePolicy.policyId }?.uuid == basePolicy.policyId)
    }

    @Test
    fun `retrieve Policies by uuid and date should return policy`() {
        //GIVEN
        createPolicy()
        val findPolicyDto = FindPolicyDto(policyId = basePolicy.policyId, requestDate = LocalDate.now())

        val retrievedPolicy = policyService.getPolicy(findPolicyDto)

        assertTrue(retrievedPolicy?.policyId == basePolicy.policyId)
    }

    @Test
    fun `update Policies with removing one insured person should update and then return policy`() {
        //GIVEN
        createPolicy()
        val newPolicy = createIntegratedPolicyDto(basePolicy.policyId)

        val updatedPolicy = policyService.updatePolicy(newPolicy)

        assertTrue(updatedPolicy.insuredPersons.size == 1)
    }

    @Test
    fun `update Policies with adding one insured person should update and then return policy`() {
        //GIVEN
        createPolicy()
        val listOfInsuredPerson =
            listOf(
                createInsuredPersonDtoTemplate(),
                createInsuredPersonDtoTemplate(
                    firstName = "daniel",
                    secondName = "Schwarz",
                    premium = BigDecimal(10.50)
                ),
                createInsuredPersonDtoTemplate(firstName = "Karsten", secondName = "Wenck", premium = BigDecimal(20))
            )
        val newPolicy = createIntegratedPolicyDto(basePolicy.policyId, listOfInsuredPerson)

        val updatedPolicy = policyService.updatePolicy(newPolicy)

        assertTrue(updatedPolicy.insuredPersons.size == 3)
    }
}
package com.hadi.kotlin.application

import com.hadi.kotlin.application.api.PolicyService
import com.hadi.kotlin.domain.FindPolicyDto
import com.hadi.kotlin.domain.IntegratedPolicyDto
import com.hadi.kotlin.infrastructure.PolicyRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PolicyServiceImplTest {

  @Autowired
  lateinit var policyService: PolicyService

  @Autowired
  lateinit var policyRepository: PolicyRepository

  lateinit var basePolicy: IntegratedPolicyDto

  @BeforeAll
  fun createPolicy() {
    val policies = createPolicyDtoTemplate()
    basePolicy = policyService.createPolicy(policies)
  }

  @Test
  fun `create Policy test should create new policies`() {
    //GIVEN
    val policies = createPolicyDtoTemplate()
    basePolicy = policyService.createPolicy(policies)

    //WHEN
    val newPolices = policyRepository.findAll()

    //THEN
    assertTrue(newPolices.find { it.uuid == basePolicy.policyId }?.uuid == basePolicy.policyId)
  }

  @Test
  fun `retrieve Policies by uuid and date should return policy`() {
    //GIVEN
    val findPolicyDto = FindPolicyDto(policyId = basePolicy.policyId, requestDate = LocalDate.now())

    //WHEN
    val retrievedPolicy = policyService.getPolicy(findPolicyDto)

    //THEN
    assertTrue(retrievedPolicy?.policyId == basePolicy.policyId)
    assertNotNull(retrievedPolicy!!.insuredPersons.first().uuid)
  }

  @Test
  fun `retrieve Policies by invalid uuid and date should return null`() {
    //GIVEN
    val findPolicyDto = FindPolicyDto(policyId = UUID.randomUUID(), requestDate = LocalDate.now())

    //WHEN
    val retrievedPolicy = policyService.getPolicy(findPolicyDto)

    //THEN
    assertNull(retrievedPolicy)
  }

  @Test
  fun `update existing policies should update and then return policies`() {
    //GIVEN
    val insuredPersonsList = listOf(
      createInsuredPersonDtoTemplate(uuid = basePolicy.insuredPersons[0].uuid, firstName = "Heydari", secondName = "Hadi"),
      createInsuredPersonDtoTemplate(uuid = basePolicy.insuredPersons[1].uuid, firstName = "Daniel", secondName = "Schwarz", premium = 1.00)
    )
    val updateRequest = createIntegratedPolicyDto(basePolicy.policyId, listOfInsuredPersons = insuredPersonsList)

    //WHEN
    val updatedPolicy = policyService.updatePolicy(updateRequest)

    //THEN
    assertTrue(updatedPolicy.insuredPersons.size == 2)
    assertTrue(updatedPolicy.insuredPersons[0].uuid == updateRequest.insuredPersons[0].uuid)
    assertTrue(updatedPolicy.insuredPersons[0].firstName == updateRequest.insuredPersons[0].firstName)
    assertTrue(updatedPolicy.insuredPersons[0].secondName == updateRequest.insuredPersons[0].secondName)
    assertTrue(updatedPolicy.insuredPersons[1].uuid == updateRequest.insuredPersons[1].uuid)
    assertTrue(updatedPolicy.insuredPersons[1].firstName == updateRequest.insuredPersons[1].firstName)
  }

  @Test
  fun `update Policies with adding new insured person should update and then return policy`() {
    //GIVEN
    val insuredPersonsList = listOf(
      createInsuredPersonDtoTemplate(uuid = basePolicy.insuredPersons[0].uuid),
      createInsuredPersonDtoTemplate(uuid = basePolicy.insuredPersons[1].uuid, firstName = "Daniel", secondName = "Schwarz", premium = 1.00),
      createInsuredPersonDtoTemplate(uuid = null, firstName = "Roger", secondName = "Water", premium = 13.00)
    )
    val updateRequest = createIntegratedPolicyDto(basePolicy.policyId, listOfInsuredPersons = insuredPersonsList)

    //WHEN
    val updatedPolicy = policyService.updatePolicy(updateRequest)

    //THEN
    assertTrue(updatedPolicy.insuredPersons.size == 3)
    assertTrue(updatedPolicy.insuredPersons[0].uuid == updateRequest.insuredPersons[0].uuid)
    assertTrue(updatedPolicy.insuredPersons[0].firstName == updateRequest.insuredPersons[0].firstName)
    assertTrue(updatedPolicy.insuredPersons[0].secondName == updateRequest.insuredPersons[0].secondName)
    assertTrue(updatedPolicy.insuredPersons[1].uuid == updateRequest.insuredPersons[1].uuid)
    assertTrue(updatedPolicy.insuredPersons[1].firstName == updateRequest.insuredPersons[1].firstName)
    assertNotNull(updatedPolicy.insuredPersons[2].uuid)
    assertTrue(updatedPolicy.insuredPersons[2].firstName == updateRequest.insuredPersons[2].firstName)
    assertTrue(updatedPolicy.insuredPersons[2].secondName == updateRequest.insuredPersons[2].secondName)
  }

  @Test
  fun `update Policies with removing one insured person should update and then return policy`() {
    //GIVEN
    createPolicy()
    val updateRequest = createIntegratedPolicyDto(basePolicy.policyId)

    //WHEN
    val updatedPolicy = policyService.updatePolicy(updateRequest)

    //THEN
    assertTrue(updatedPolicy.insuredPersons.size == 1)
    assertTrue(updatedPolicy.insuredPersons.first().firstName == updateRequest.insuredPersons.first().firstName)
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
          premium = 10.50
        ),
        createInsuredPersonDtoTemplate(firstName = "Karsten", secondName = "Wenck", premium = 20.00)
      )
    val newPolicy = createIntegratedPolicyDto(basePolicy.policyId, listOfInsuredPerson)

    val updatedPolicy = policyService.updatePolicy(newPolicy)

    assertTrue(updatedPolicy.insuredPersons.size == 3)
  }
}

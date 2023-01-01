package com.hadi.kotlin.application

import com.hadi.kotlin.domain.IntegratedPolicyDto
import com.hadi.kotlin.infrastructure.PolicyRepository
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ChallengeCodeWsV1Test {

  @Autowired
  lateinit var policyRepository: PolicyRepository

  @Autowired
  lateinit var restTemplate: TestRestTemplate

  val policyPath = "/policyService/"

  @Test
  fun `get all Policies test should return OK as HTTP status`() {
    //WHEN
    val result = restTemplate.getForEntity(policyPath, List::class.java);

    //THEN
    assertNotNull(result)
    assertEquals(HttpStatus.OK, result?.statusCode)
  }

  @Test
  fun `create Policy test with valid start date should save a policy in DB`() {
    //Given
    val newPolicy = createPolicyDtoTemplate()

    //WHEN
    val foundPolicy = restTemplate.postForEntity(policyPath, newPolicy, IntegratedPolicyDto::class.java)

    //THEN
    assertNotNull(foundPolicy)
    assertEquals(HttpStatus.OK, foundPolicy?.statusCode)
    val savedPolicy = policyRepository.findByUuid(foundPolicy.body!!.policyId)
    assertEquals(savedPolicy!!.uuid, foundPolicy.body!!.policyId)
  }

  @Test
  fun `create Policy test with invalid start date should throw an Exception`() {
    //Given
    val newPolicy = createPolicyDtoTemplate(LocalDate.now().minusDays(1))

    //WHEN
    val result = restTemplate.postForEntity(policyPath, newPolicy, Exception::class.java)

    //THEN
    assertNotNull(result)
    assertEquals(HttpStatus.BAD_REQUEST, result?.statusCode)
  }

  @Test
  fun `get a saved policy test should return a policy`() {
    //Given
    val newPolicy = createPolicyDtoTemplate()
    val savedPolicy = restTemplate.postForEntity(policyPath, newPolicy, IntegratedPolicyDto::class.java)
    val getPolicyUrl = "$policyPath/${newPolicy.startDate}/requestDate/${savedPolicy.body!!.policyId}/policyId"

    //WHEN
    val foundPolicy = restTemplate.getForEntity(getPolicyUrl, IntegratedPolicyDto::class.java)

    //THEN
    assertNotNull(foundPolicy)
    assertEquals(HttpStatus.OK, foundPolicy?.statusCode)
    assertEquals(savedPolicy.body!!.policyId, foundPolicy.body!!.policyId)
  }

  @Test
  fun `get a policy with null create date should go with current date and return the saved policy`() {
    //Given
    val newPolicy = createPolicyDtoTemplate()
    val savedPolicy = restTemplate.postForEntity(policyPath, newPolicy, IntegratedPolicyDto::class.java)
    val getPolicyUrl = "$policyPath/ /requestDate/${savedPolicy.body!!.policyId}/policyId"

    //WHEN
    val foundPolicy = restTemplate.getForEntity(getPolicyUrl, IntegratedPolicyDto::class.java)

    //THEN
    assertNotNull(foundPolicy)
    assertEquals(HttpStatus.OK, foundPolicy?.statusCode)
    assertEquals(savedPolicy.body!!.policyId, foundPolicy.body!!.policyId)
  }

  @Test
  fun `get a policy with wrong policyId test should return an empty list`() {
    //Given
    val newPolicy = createPolicyDtoTemplate()
    val getPolicyUrl = "$policyPath/${newPolicy.startDate}/requestDate/${UUID.randomUUID()}/policyId"

    //WHEN
    val foundPolicy = restTemplate.getForEntity(getPolicyUrl, IntegratedPolicyDto::class.java)

    //THEN
    assertNotNull(foundPolicy)
    assertEquals(HttpStatus.OK, foundPolicy?.statusCode)
    assertEquals(foundPolicy.body, null)
  }

  @Test
  fun `update a policy test should update an existing policy`() {
    //Given
    val newPolicy = createPolicyDtoTemplate()
    val savedPolicy = restTemplate.postForEntity(policyPath, newPolicy, IntegratedPolicyDto::class.java)
    val updateDto = createIntegratedPolicyDto(policyId = savedPolicy.body!!.policyId, startDate = LocalDate.now().plusDays(2))

    //WHEN
    val updatedPolicy = restTemplate.patchForObject(policyPath, updateDto, IntegratedPolicyDto::class.java)

    //THEN
    assertNotNull(updatedPolicy)
    assertEquals(updatedPolicy.startDate, LocalDate.now().plusDays(2))
  }

  @Test
  fun `update a policy with start date in the past test should throw an error`() {
    //Given
    val newPolicy = createPolicyDtoTemplate()
    val savedPolicy = restTemplate.postForEntity(policyPath, newPolicy, IntegratedPolicyDto::class.java)
    val updateDto = createIntegratedPolicyDto(policyId = savedPolicy.body!!.policyId, startDate = LocalDate.now().minusDays(2))

    //WHEN
    val updatedPolicy = restTemplate.patchForObject(policyPath, updateDto, Exception::class.java)

    //THEN
    assertNotNull(updatedPolicy)
    assertEquals(Exception::class.java, updatedPolicy.javaClass)
  }
}

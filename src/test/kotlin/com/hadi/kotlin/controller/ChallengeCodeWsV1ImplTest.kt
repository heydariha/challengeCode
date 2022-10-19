package com.hadi.kotlin.application

import com.hadi.kotlin.domain.IntegratedPolicyDto
import com.hadi.kotlin.infrastructure.PolicyRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ChallengeCodeWsV1ImplTest {

    @Autowired
    lateinit var policyService: PolicyService

    @Autowired
    lateinit var policyRepository: PolicyRepository

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    val policyPath = "/policyService/policies"

    @Test
    fun `create Policy test should create two new policies`() {
        //GIVEN
        val basePolicy = restTemplate.postForEntity(policyPath, createPolicyDtoTemplate(), IntegratedPolicyDto::class.java)

        //WHEN
        val newPolices = policyRepository.findAll()

        //THEN
        assertTrue(newPolices.find { it.uuid == basePolicy.body!!.policyId }?.uuid == basePolicy.body!!.policyId)
    }

    @Test
    fun `get Policy test should get two new policies`() {
        //GIVEN
        val basePolicy = restTemplate.postForEntity(policyPath, createPolicyDtoTemplate(), IntegratedPolicyDto::class.java)

        //WHEN
        val newPolices = restTemplate.getForEntity("$policyPath/${basePolicy.body!!.startDate}/requestDate/${basePolicy.body!!.policyId}/policyId", IntegratedPolicyDto::class.java)

        //THEN
        assertTrue(newPolices.body!!.policyId  == basePolicy.body!!.policyId)
    }
}
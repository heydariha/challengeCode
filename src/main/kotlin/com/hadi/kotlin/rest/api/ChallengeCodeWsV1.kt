package com.hadi.kotlin.rest.api

import com.hadi.kotlin.domain.IntegratedPolicyDto
import com.hadi.kotlin.domain.PolicyDto
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("/policyService")
interface ChallengeCodeWsV1 {

  @GetMapping("/{requestDate}/requestDate/{policyId}/policyId")
  fun getPolicy(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                @PathVariable requestDate: LocalDate?,
                @PathVariable policyId: UUID
  ): IntegratedPolicyDto?

  @GetMapping("/")
  fun getPolicies(): List<IntegratedPolicyDto>

  @PostMapping("/")
  fun createPolicy(@RequestBody @Valid policyDto: PolicyDto): IntegratedPolicyDto

  @PatchMapping("/")
  fun updatePolicy(@RequestBody @Valid policyResponseDto: IntegratedPolicyDto): IntegratedPolicyDto
}

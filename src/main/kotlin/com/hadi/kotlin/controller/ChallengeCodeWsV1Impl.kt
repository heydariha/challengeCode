package com.hadi.kotlin.controller

import com.hadi.kotlin.application.PolicyService
import com.hadi.kotlin.domain.FindPolicyDto
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
class ChallengeCodeWsV1Impl(val policyService: PolicyService) {

  @GetMapping("/policy/{requestDate}/requestDate/{policyId}/policyId")
  fun getPolicy(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                @PathVariable requestDate: LocalDate,
                @PathVariable policyId: UUID
  ): IntegratedPolicyDto? {
    return policyService.getPolicy(FindPolicyDto(requestDate = requestDate, policyId = policyId))
  }

  @GetMapping("/policy")
  fun getPolicies(): List<IntegratedPolicyDto> {
    return policyService.getPolicy()
  }

  @PostMapping("/policy")
  fun createPolicy(@RequestBody @Valid policyDto: PolicyDto): IntegratedPolicyDto {
    return policyService.createPolicy(policyDto)
  }

  @PatchMapping("/policy")
  fun updatePolicy(@RequestBody @Valid policyResponseDto: IntegratedPolicyDto): IntegratedPolicyDto {
    return policyService.updatePolicy(policyResponseDto)
  }
}

package com.hadi.kotlin.rest.impl

import com.hadi.kotlin.application.api.PolicyService
import com.hadi.kotlin.domain.FindPolicyDto
import com.hadi.kotlin.domain.IntegratedPolicyDto
import com.hadi.kotlin.domain.PolicyDto
import com.hadi.kotlin.rest.api.ChallengeCodeWsV1
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("/policyService")
class ChallengeCodeWsV1Impl(val policyService: PolicyService) : ChallengeCodeWsV1 {

  @GetMapping("/{requestDate}/requestDate/{policyId}/policyId")
  override fun getPolicy(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                         @PathVariable requestDate: LocalDate?,
                         @PathVariable policyId: UUID
  ): IntegratedPolicyDto? {
    return policyService.getPolicy(FindPolicyDto(requestDate = requestDate, policyId = policyId))
  }

  @GetMapping("/")
  override fun getPolicies(): List<IntegratedPolicyDto> {
    return policyService.getPolicy()
  }

  @PostMapping("/")
  override fun createPolicy(@RequestBody @Valid policyDto: PolicyDto): IntegratedPolicyDto {
    return policyService.createPolicy(policyDto)
  }

  @PatchMapping("/")
  override fun updatePolicy(@RequestBody @Valid policyResponseDto: IntegratedPolicyDto): IntegratedPolicyDto {
    return policyService.updatePolicy(policyResponseDto)
  }
}

package com.hadi.kotlin.controller

import com.hadi.kotlin.application.PolicyService
import com.hadi.kotlin.domain.FindPolicyDto
import com.hadi.kotlin.domain.PolicyDto
import com.hadi.kotlin.domain.IntegratedPolicyDto
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/policyService")
class ChallengeCodeWsV1Impl(val policyService: PolicyService) {

    @GetMapping("/policies")
    fun getPolicy(@RequestBody @Valid findPolicyDto: FindPolicyDto): IntegratedPolicyDto? {
        return policyService.getPolicy(findPolicyDto)
    }

    @PostMapping("/policies")
    fun createPolicy(@RequestBody @Valid policyDto: PolicyDto): IntegratedPolicyDto {
        return policyService.createPolicy(policyDto)
    }

    @PatchMapping("/policies")
    fun updatePolicy(@RequestBody @Valid policyResponseDto: IntegratedPolicyDto): IntegratedPolicyDto {
        return policyService.updatePolicy(policyResponseDto)
    }

}
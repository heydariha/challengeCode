package com.embea.kotlin.controller

import com.embea.kotlin.application.PolicyService
import com.embea.kotlin.domain.FindPolicyDto
import com.embea.kotlin.domain.PolicyDto
import com.embea.kotlin.domain.IntegratedPolicyDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/policyService")
class embeaWsV1Impl(val policyService: PolicyService) {

    @GetMapping("/policies")
    fun getPolicy(@RequestBody findPolicyDto: FindPolicyDto): IntegratedPolicyDto? {
        return policyService.getPolicy(findPolicyDto)
    }

    @PostMapping("/policies")
    fun createPolicy(@RequestBody policyDto: PolicyDto): IntegratedPolicyDto {
        return policyService.createPolicy(policyDto)
    }

    @PutMapping("/policies")
    fun updatePolicy(@RequestBody policyResponseDto: IntegratedPolicyDto): IntegratedPolicyDto {
        return policyService.updatePolicy(policyResponseDto)
    }

}
package com.hadi.kotlin.controller

import com.hadi.kotlin.application.PolicyService
import com.hadi.kotlin.domain.FindPolicyDto
import com.hadi.kotlin.domain.PolicyDto
import com.hadi.kotlin.domain.IntegratedPolicyDto
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.validation.Valid
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/policyService")
class ChallengeCodeWsV1Impl(val policyService: PolicyService) {

    @GetMapping("/policies/{requestDate}/requestDate/{policyId}/policyId")
    fun getPolicy(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                  @PathVariable requestDate: LocalDate,
                  @PathVariable policyId: UUID
    ): IntegratedPolicyDto? {
        return policyService.getPolicy(FindPolicyDto(requestDate = requestDate, policyId = policyId))
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
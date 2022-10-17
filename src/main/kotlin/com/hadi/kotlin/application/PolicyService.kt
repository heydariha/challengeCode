package com.hadi.kotlin.application

import com.hadi.kotlin.domain.FindPolicyDto
import com.hadi.kotlin.domain.PolicyDto
import com.hadi.kotlin.domain.IntegratedPolicyDto

interface PolicyService {

    fun getPolicy(findPolicyDto: FindPolicyDto): IntegratedPolicyDto?
    fun createPolicy(policyDto: PolicyDto): IntegratedPolicyDto
    fun updatePolicy(integratedPolicyDto: IntegratedPolicyDto): IntegratedPolicyDto
}
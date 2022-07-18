package com.embea.kotlin.application

import com.embea.kotlin.domain.FindPolicyDto
import com.embea.kotlin.domain.PolicyDto
import com.embea.kotlin.domain.IntegratedPolicyDto

interface PolicyService {

    fun getPolicy(findPolicyDto: FindPolicyDto): IntegratedPolicyDto?
    fun createPolicy(policyDto: PolicyDto): IntegratedPolicyDto
    fun updatePolicy(integratedPolicyDto: IntegratedPolicyDto): IntegratedPolicyDto
}
package com.hadi.kotlin.application.api

import com.hadi.kotlin.domain.InsuredPerson
import com.hadi.kotlin.domain.InsuredPersonDto
import java.util.*

interface InsuredPersonService {
  fun getInsuredPerson(uuid: UUID): InsuredPerson
  fun removeAllInsuredPersonByPolicyId(uuid: UUID)
  fun assembleInsuredPersonForUpdate(insuredPersonDto: List<InsuredPersonDto>): List<InsuredPerson>
}

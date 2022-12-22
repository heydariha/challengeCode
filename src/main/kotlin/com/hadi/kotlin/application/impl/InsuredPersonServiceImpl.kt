package com.hadi.kotlin.application.impl

import com.hadi.kotlin.application.api.InsuredPersonService
import com.hadi.kotlin.config.BadRequestException
import com.hadi.kotlin.config.NotFoundException
import com.hadi.kotlin.domain.InsuredPerson
import com.hadi.kotlin.domain.InsuredPersonDto
import com.hadi.kotlin.infrastructure.InsuredPersonRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class InsuredPersonServiceImpl(val insuredPersonRepository: InsuredPersonRepository) : InsuredPersonService {
  override fun getInsuredPerson(uuid: UUID): InsuredPerson {
    try {
      return insuredPersonRepository.findByUuid(uuid)
    } catch (e: Exception) {
      throw NotFoundException(
        message = "Insured Person not found",
        detailMessage = "Policy with Id : $uuid not found"
      )
    }
  }

  override fun assembleInsuredPersonForUpdate(insuredPersonDto: List<InsuredPersonDto>): List<InsuredPerson> {
    val insuredPersonList = mutableListOf<InsuredPerson>()
    insuredPersonDto.forEach {
      insuredPersonList.add(when (it.uuid) {
        null -> {
          InsuredPerson(
            id = 0,
            uuid = UUID.randomUUID(),
            firstName = it.firstName,
            secondName = it.secondName,
            premium = it.premium,
            policy = null
          )
        }
        else -> {
          val insuredPerson = getInsuredPerson(it.uuid)
          insuredPerson.firstName = it.firstName
          insuredPerson.secondName = it.secondName
          insuredPerson.premium = it.premium
          insuredPerson
        }
      })
    }
    return insuredPersonList
  }

  override fun removeAllInsuredPersonByPolicyId(uuid: UUID){
    try {
      val insuredPerson = getInsuredPerson(uuid)
      insuredPersonRepository.deleteByPolicy(insuredPerson.id)
    }catch (e : Exception){
      throw BadRequestException(
        message = "Unable to Delete Insured Person",
        detailMessage = "Unable to remove insured persons with Policy Id : $uuid"
      )
    }

  }
}

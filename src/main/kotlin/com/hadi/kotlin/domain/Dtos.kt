package com.hadi.kotlin.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Future
import javax.validation.constraints.FutureOrPresent
import javax.validation.constraints.NotNull

data class FindPolicyDto(
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull @field:Future val requestDate: LocalDate?,
  @NotNull val policyId: UUID,
)

data class PolicyDto(
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull
  @field:FutureOrPresent val startDate: LocalDate,
  @NotNull val insuredPersons: List<InsuredPersonDto>
)

data class InsuredPersonDto(
  @NotNull val uuid: UUID?,
  @NotNull val firstName: String,
  @NotNull val secondName: String,
  @NotNull val premium: Double
)

data class IntegratedPolicyDto(
  @NotNull val policyId: UUID,
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull @field:FutureOrPresent val startDate: LocalDate,
  @NotNull val insuredPersons: List<InsuredPersonDto>,
  @NotNull val totalPremium: Double?
)

package com.hadi.kotlin.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Future
import javax.validation.constraints.NotNull


data class FindPolicyDto(
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull val requestDate: LocalDate,
  @NotNull val policyId: UUID,
)

data class PolicyDto(
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull @Future val startDate: LocalDate,
  @NotNull val insuredPersons: List<InsuredPersonDto>
)

data class InsuredPersonDto(
  @NotNull val firstName: String,
  @NotNull val secondName: String,
  @NotNull val premium: BigDecimal
)

data class IntegratedPolicyDto(
  @NotNull val policyId: UUID,
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull val startDate: LocalDate,
  @NotNull val insuredPersons: List<InsuredPersonDto>,
  @NotNull val totalPremium: BigDecimal
)

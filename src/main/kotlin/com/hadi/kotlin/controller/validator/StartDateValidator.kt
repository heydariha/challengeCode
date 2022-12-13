package com.hadi.kotlin.controller.validator

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class StartDateValidator : ConstraintValidator<ValidateStartDate?, String?> {

  override fun initialize(constraintAnnotation: ValidateStartDate?) {}

  override fun isValid(value: String?, cxt: ConstraintValidatorContext?): Boolean {
    return when {
      value.equals("2020-12-30") -> true
      else -> throw IllegalArgumentException("Start date is not valid. It must be in the future")
    }
  }

}

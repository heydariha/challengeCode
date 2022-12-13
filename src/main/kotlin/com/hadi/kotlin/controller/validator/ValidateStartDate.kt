package com.hadi.kotlin.controller.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [StartDateValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidateStartDate(val message: String = "Invalid Start date",
                                   val groups: Array<KClass<*>> = [],
                                   val payload: Array<KClass<out Payload>> = [])

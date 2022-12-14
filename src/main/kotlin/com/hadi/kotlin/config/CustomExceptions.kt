package com.hadi.kotlin.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver

@ControllerAdvice
class ExceptionControllerAdvice {

  @ExceptionHandler
  fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorMessageModel> {

    val errorMessage = ErrorMessageModel(
      HttpStatus.BAD_REQUEST.value(),
      ex.message
    )
    return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
  }

  @ExceptionHandler
  fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorMessageModel> {

    val errorMessage = ErrorMessageModel(
      HttpStatus.BAD_REQUEST.value(),
      ex.message
    )
    return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
  }

  @ExceptionHandler
  fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorMessageModel> {

    val errorMessage = ErrorMessageModel(
      HttpStatus.BAD_REQUEST.value(),
      ex.message
    )
    return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
  }

  @ExceptionHandler
  fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorMessageModel> {

    val errorMessage = ErrorMessageModel(
      HttpStatus.NOT_FOUND.value(),
      ex.detailMessage
    )
    return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
  }
}

class ErrorMessageModel(
  var status: Int? = null,
  var message: String? = null
)

class NotFoundException(
    override val message: String,
    val detailMessage: String?
) : RuntimeException()

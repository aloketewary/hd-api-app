package com.hardwaredash.app.config

import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.util.httpCommonError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GeneralExceptionHandler {

    @ExceptionHandler(Exception::class)
    protected fun handleException(ex: Exception): CommonHttpResponse<String> {
        return httpCommonError(ex)
    }

}
package com.hardwaredash.app.util

import com.hardwaredash.app.model.CommonHttpError
import com.hardwaredash.app.model.CommonHttpResponse
import org.springframework.http.HttpStatus

fun <T> httpPostSuccess(result: T, desc: String = "Insert success"): CommonHttpResponse<T> {
    return CommonHttpResponse(
        desc = desc,
        result = result,
        status = HttpStatus.CREATED.value(),
        success = true
    )
}

fun <T> httpGetSuccess(result: T, desc: String = "Get success"): CommonHttpResponse<T> {
    return CommonHttpResponse(
        desc = desc,
        result = result,
        status = HttpStatus.OK.value(),
        success = true
    )
}

fun <T> httpPatchSuccess(result: T, desc: String = "Patch Success"): CommonHttpResponse<T> {
    return CommonHttpResponse(
        desc = desc,
        result = result,
        status = HttpStatus.OK.value(),
        success = true
    )
}

fun <T> httpCommonError(exception: Exception?, desc: String = "Error"): CommonHttpResponse<T> {
    return CommonHttpResponse(
        desc = desc,
        result = null,
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        success = false,
        error = CommonHttpError(exception?.message ?: desc)
    )
}

fun <T> httpDeleteSuccess(result: T, desc: String = "Delete success"): CommonHttpResponse<T> {
    return CommonHttpResponse(
        desc = desc,
        result = result,
        status = HttpStatus.OK.value(),
        success = true
    )
}
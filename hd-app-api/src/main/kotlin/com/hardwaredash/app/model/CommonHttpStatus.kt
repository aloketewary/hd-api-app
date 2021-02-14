package com.hardwaredash.app.model

data class CommonHttpResponse<T>(
    val desc: String,
    val result: T?,
    val success: Boolean,
    val status: Int,
    val error: CommonHttpError? = null,
)

data class CommonHttpError(
    val message: String?,
)
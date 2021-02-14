package com.hardwaredash.app.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConfigResponse(
    val id: String?,
    val key: String,
    val value: String,
    val usedFor: String,
    val isActive: Boolean,
)
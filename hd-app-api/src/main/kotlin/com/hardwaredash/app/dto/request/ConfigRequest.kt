package com.hardwaredash.app.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ConfigRequest(
    val key: String,
    val value: String,
    @JsonProperty("used_for")
    val usedFor: String,
    @JsonProperty("is_active")
    val isActive: Boolean,
)
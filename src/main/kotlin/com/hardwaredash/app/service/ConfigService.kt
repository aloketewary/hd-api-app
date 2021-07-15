package com.hardwaredash.app.service

import com.hardwaredash.app.dto.ConfigRequest
import com.hardwaredash.app.dto.ConfigResponse
import com.hardwaredash.app.model.CommonHttpResponse


interface ConfigService {
    fun getAllConfig(usedFor: String): CommonHttpResponse<Map<String, Any>>

    fun getAllConfigForAdmin(usedFor: String): CommonHttpResponse<List<ConfigResponse>>

    fun insert(config: ConfigRequest): CommonHttpResponse<ConfigResponse>

    fun update(id: String, config: ConfigRequest): CommonHttpResponse<ConfigResponse>

    fun delete(id: String): CommonHttpResponse<ConfigResponse>

}
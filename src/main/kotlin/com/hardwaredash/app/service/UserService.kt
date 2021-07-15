package com.hardwaredash.app.service

import com.hardwaredash.app.dto.ConfigRequest
import com.hardwaredash.app.dto.ConfigResponse
import com.hardwaredash.app.entity.UserEntity
import com.hardwaredash.app.model.CommonHttpResponse
import org.springframework.data.domain.Page


interface UserService {

    fun getAllUsersForAdmin(page: Int, size: Int): CommonHttpResponse<Page<UserEntity>>

    fun addNewUser(config: ConfigRequest): CommonHttpResponse<ConfigResponse>

    fun updateUser(id: String, config: ConfigRequest): CommonHttpResponse<ConfigResponse>

    fun deleteUser(id: String): CommonHttpResponse<ConfigResponse>

}
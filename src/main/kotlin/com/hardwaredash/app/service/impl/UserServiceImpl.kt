package com.hardwaredash.app.service.impl

import com.hardwaredash.app.dto.ConfigRequest
import com.hardwaredash.app.dto.ConfigResponse
import com.hardwaredash.app.entity.UserEntity
import com.hardwaredash.app.middleware.UserMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.service.UserService
import com.hardwaredash.app.util.httpCommonError
import com.hardwaredash.app.util.httpGetSuccess
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}

@Service
class UserServiceImpl(
    private val userMiddleware: UserMiddleware
) : UserService {
    override fun getAllUsersForAdmin(page: Int, size: Int): CommonHttpResponse<Page<UserEntity>> {
        return try {
            val pageRequest = PageRequest.of(page, size)
            val allUsers = userMiddleware.getAll(pageRequest)
            httpGetSuccess(allUsers)
        } catch (e: Exception) {
            logger.error(e.message, e)
            httpCommonError(e)
        }
    }

    override fun addNewUser(config: ConfigRequest): CommonHttpResponse<ConfigResponse> {
        TODO("Not yet implemented")
    }

    override fun updateUser(id: String, config: ConfigRequest): CommonHttpResponse<ConfigResponse> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: String): CommonHttpResponse<ConfigResponse> {
        TODO("Not yet implemented")
    }


}
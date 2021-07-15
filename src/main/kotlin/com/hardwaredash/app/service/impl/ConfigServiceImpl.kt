package com.hardwaredash.app.service.impl

import com.hardwaredash.app.dto.ConfigRequest
import com.hardwaredash.app.dto.ConfigResponse
import com.hardwaredash.app.entity.ConfigEntity
import com.hardwaredash.app.middleware.ConfigMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.service.ConfigService
import com.hardwaredash.app.util.*
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

private val logger = KotlinLogging.logger {}

@Service
class ConfigServiceImpl(
    private val configMiddleware: ConfigMiddleware
) : ConfigService {
    override fun getAllConfig(usedFor: String): CommonHttpResponse<Map<String, Any>> {
        logger.info { "Get all config for = $usedFor" }
        return try {
            val allConfig = configMiddleware.getAllConfig(usedFor)
            httpGetSuccess(allConfig)
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    override fun getAllConfigForAdmin(usedFor: String): CommonHttpResponse<List<ConfigResponse>> {
        logger.info { "Get all admin related config for = $usedFor" }
        return try {
            val allConfig = configMiddleware.getAllConfigForAdmin(usedFor).map {
                it.convertToDtoResponse()
            }
            httpGetSuccess(allConfig)
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    override fun insert(config: ConfigRequest): CommonHttpResponse<ConfigResponse> {
        logger.info { "Insert new config where data = $config" }
        return try {
            val configEntity = ConfigEntity(
                key = config.key,
                value = config.value,
                usedFor = config.usedFor,
                isActive = config.isActive,
                createdBy = "ADMIN",
                createdDate = OffsetDateTime.now(),
            )
            val savedConfig = configMiddleware.insert(configEntity)
            val configResponse = savedConfig.convertToDtoResponse()
            httpPostSuccess(configResponse, "Config Created success")
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    override fun update(id: String, config: ConfigRequest): CommonHttpResponse<ConfigResponse> {
        logger.info { "Update existing config id = $id where data = $config" }
        return try {
            val configId = configMiddleware.getById(id)
            when {
                configId.isPresent -> {
                    val configEntity = ConfigEntity(
                        id = configId.get().id,
                        key = config.key,
                        value = config.value,
                        usedFor = config.usedFor,
                        isActive = config.isActive,
                        createdBy = configId.get().createdBy,
                        createdDate = configId.get().createdDate,
                        modifiedBy = "ADMIN",
                        modifiedDate = OffsetDateTime.now()
                    )
                    val updatedConfig = configMiddleware.update(configEntity)
                    httpPatchSuccess(updatedConfig.convertToDtoResponse(), "Config updated success")
                }
                else -> httpCommonError(Exception("No Config found with id=$id"))
            }
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    override fun delete(id: String): CommonHttpResponse<ConfigResponse> {
        logger.info { "Delete existing config where id = $id" }
        return try {
            val deleteById = configMiddleware.deleteById(id)
            if (deleteById.isPresent) {
                httpDeleteSuccess(deleteById.get().convertToDtoResponse())
            } else {
                httpCommonError(Exception("Delete Failure for id=$id"))
            }
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

}
package com.hardwaredash.app.service

import com.hardwaredash.app.dto.ProductUnitRequest
import com.hardwaredash.app.dto.ProductUnitResponse
import com.hardwaredash.app.entity.ProductUnitEntity
import com.hardwaredash.app.middleware.ProductUnitMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.util.httpCommonError
import com.hardwaredash.app.util.httpGetSuccess
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

private val logger = KotlinLogging.logger {}

@Service
class ProductUnitService(
    private val productUnitMiddleware: ProductUnitMiddleware
) {

    /**
     * Get Dashboard Info
     */
    fun getAllProductUnits(): CommonHttpResponse<List<ProductUnitResponse>> {
        logger.info { "Get All Product Units" }
        return try {
            val allProducts = productUnitMiddleware.getAllProductUnits()
            httpGetSuccess(allProducts.map { it.convertToDtoResponse() })
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    fun insertProductUnit(productUnitRequest: ProductUnitRequest): CommonHttpResponse<ProductUnitResponse> {
        logger.info { "Get All Product Units" }
        return try {
            val unitEntity =  ProductUnitEntity(
                id = productUnitRequest.id,
                name = productUnitRequest.name,
                unit = productUnitRequest.unit,
                isActive = productUnitRequest.isActive,
                multiple = productUnitRequest.multiple.toDouble(),
                multipleWith = productUnitRequest.multipleWith,
                readOnly = productUnitRequest.readOnly,
                createdBy = "ADMIN",
                createdDate = OffsetDateTime.now(),
            )
            val productUnitEntity = productUnitMiddleware.insert(unitEntity)
            httpGetSuccess(productUnitEntity.convertToDtoResponse())
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }
}
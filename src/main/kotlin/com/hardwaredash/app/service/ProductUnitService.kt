package com.hardwaredash.app.service

import com.hardwaredash.app.dto.ProductUnitRequest
import com.hardwaredash.app.dto.ProductUnitResponse
import com.hardwaredash.app.model.CommonHttpResponse


interface ProductUnitService {

    /**
     * Get Dashboard Info
     */
    fun getAllProductUnits(): CommonHttpResponse<List<ProductUnitResponse>>

    fun insertProductUnit(productUnitRequest: ProductUnitRequest): CommonHttpResponse<ProductUnitResponse>
}
package com.hardwaredash.app.service

import com.hardwaredash.app.dto.ProductRequest
import com.hardwaredash.app.dto.ProductResponse
import com.hardwaredash.app.model.CommonHttpResponse
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile


interface ProductService {

    fun getAllProducts(): CommonHttpResponse<List<ProductResponse>>

    fun insert(product: ProductRequest): CommonHttpResponse<ProductResponse>

    fun insertBulkProduct(multipartFile: MultipartFile): CommonHttpResponse<String>

    fun update(id: String, product: ProductRequest): CommonHttpResponse<ProductResponse>

    fun delete(id: String): CommonHttpResponse<ProductResponse>

    fun deleteAllById(ids: String): CommonHttpResponse<List<ProductResponse>>

    fun getAllPageableProducts(
        filter: String,
        sortOrder: String,
        page: Int,
        size: Int
    ): CommonHttpResponse<Page<ProductResponse>>
}
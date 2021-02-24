package com.hardwaredash.app.dto.request

import com.fasterxml.jackson.annotation.JsonInclude

data class ProductRequest(
    val productName: String,
    val productVariantRequest: ProductVariantRequest,
    val isActive: Boolean,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductVariantRequest(
    val parentId: String,
    val variant: String,
    val variantName: String,
    val stockTotal: Int,
    val wholeSalePrice: Double,
    val buyPrice: Double,
    val onSale: Boolean,
    val onSalePrice: Double,
    val isActive: Boolean,
    val sellingPrice: Double,
)
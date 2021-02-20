package com.hardwaredash.app.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductResponse(
    val id: String?,
    val productName: String,
    val productVariant: ProductVariantResponse,
    val isActive: Boolean,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductVariantResponse(
    val id: String?,
    val parentId: String,
    val variant: String,
    val variantName: String,
    val stockTotal: Int,
    val wholeSalePrice: Double,
    val buyPrice: Double,
    val onSale: Boolean,
    val onSalePrice: Double,
    val sellingPrice: Double,
    val isActive: Boolean,
)
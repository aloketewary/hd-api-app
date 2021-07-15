package com.hardwaredash.app.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConfigResponse(
    val id: String?,
    val key: String,
    val value: String,
    val usedFor: String,
    val isActive: Boolean,
)


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
    val unit: String,
    val multiText: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductUnitResponse(
    val id: String? = null,
    val name: String,
    val unit: String,
    val multiple: Double,
    val isActive: Boolean,
    val readOnly: Boolean,
    val multipleWith: String? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MessageResponse(var message: String)

@JsonInclude(JsonInclude.Include.NON_NULL)
class JwtResponse(
    var accessToken: String,
    var id: String?,
    var email: String,
    val roles: List<String>
) {
    var tokenType = "Bearer"
}
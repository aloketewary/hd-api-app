package com.hardwaredash.app.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


@ApiModel("Product Unit Request")
data class ProductUnitRequest(
    val id: String? = null,
    val name: String,
    val unit: String,
    val multiple: String,
    val multipleWith: String? = null,
    val isActive: Boolean,
    val readOnly: Boolean,
)

@ApiModel("Product Request")
data class ProductRequest(
    val productName: String,
    val productVariant: ProductVariantRequest,
    val isActive: Boolean,
)

@ApiModel("Product Variant Request")
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
    val unit: String,
    val multiText: String
)

@ApiModel("Config Request")
data class ConfigRequest(
    val key: String,
    val value: String,
    @JsonProperty("used_for")
    val usedFor: String,
    @JsonProperty("is_active")
    val isActive: Boolean,
)

@ApiModel("Signup Request")
data class SignupRequest(
    @Size(max = 50) @NotBlank @Email
    val email: String,
    val roles: Set<String>,
    @Size(min = 6, max = 40)
    val password: @NotBlank String
)

@ApiModel("Login Request")
class LoginRequest(
    val username: @NotBlank String,
    val password: @NotBlank String
)
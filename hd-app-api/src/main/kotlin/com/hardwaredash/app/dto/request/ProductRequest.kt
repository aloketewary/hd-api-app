package com.hardwaredash.app.dto.request

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductRequest(
    @JsonProperty("product_name")
    val productName: String,
    @JsonProperty("product_variant")
    val productVariantRequest: ProductVariantRequest,
    @JsonProperty("is_active")
    val isActive: Boolean,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductVariantRequest(
    @JsonProperty("parent_id")
    val parentId: String,
    @JsonProperty("variant")
    val variant: String,
    @JsonProperty("variant_name")
    val variantName: String,
    @JsonProperty("stock_total")
    val stockTotal: Int,
    @JsonProperty("whole_sale_price")
    val wholeSalePrice: Double,
    @JsonProperty("buy_price")
    val buyPrice: Double,
    @JsonProperty("on_sale")
    val onSale: Boolean,
    @JsonProperty("on_sale_price")
    val onSalePrice: Double,
    @JsonProperty("is_active")
    val isActive: Boolean,
    @JsonProperty("selling_price")
    val sellingPrice: Double,
)
package com.hardwaredash.app.entity

import com.hardwaredash.app.dto.ProductResponse
import com.hardwaredash.app.dto.ProductVariantResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.OffsetDateTime

@Document(collection = "products")
data class ProductEntity(
    @Id val id: String? = null,
    @Field("product_name") val productName: String,
    @Field("product_variants") val productVariants: ProductVariants,
    @Field("is_active") val isActive: Boolean,
    @Field("created_by") val createdBy: String?,
    @Field("created_date") val createdDate: OffsetDateTime?,
    @Field("modified_by") val modifiedBy: String? = null,
    @Field("modified_date") val modifiedDate: OffsetDateTime? = null
) {
    fun convertToResponse(): ProductResponse {
        return ProductResponse(
            id = this.id,
            productName = this.productName,
            productVariant = this.productVariants.convertToResponse(),
            isActive = this.isActive
        )
    }
}

@Document(collection = "products_variants")
data class ProductVariants(
    @Id val id: String? = null,
    @Field("parent_id") val parentId: String,
    @Field val variant: String,
    @Field("variant_name") val variantName: String,
    @Field("stock_total") val stockTotal: Int,
    @Field("whole_sale_price") val wholeSalePrice: Double,
    @Field("buy_price") val buyPrice: Double,
    @Field("unit") val unit: String,
    @Field("multi_text") val multiText: String,
    @Field("on_sale") val onSale: Boolean,
    @Field("on_sale_price") val onSalePrice: Double,
    @Field("selling_price") val sellingPrice: Double,
    @Field("is_active") val isActive: Boolean,
    @Field("created_by") val createdBy: String?,
    @Field("created_date") val createdDate: OffsetDateTime?,
    @Field("modified_by") val modifiedBy: String? = null,
    @Field("modified_date") val modifiedDate: OffsetDateTime? = null
) {
    fun convertToResponse(): ProductVariantResponse {
        return ProductVariantResponse(
            id = this.id,
            parentId = this.parentId,
            variant = this.variant,
            variantName = this.variantName,
            stockTotal = this.stockTotal,
            buyPrice = this.buyPrice,
            wholeSalePrice = this.wholeSalePrice,
            onSale = this.onSale,
            onSalePrice = this.onSalePrice,
            sellingPrice = this.sellingPrice,
            isActive = this.isActive,
            unit = this.unit,
            multiText = this.multiText
        )
    }
}
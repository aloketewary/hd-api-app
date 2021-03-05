package com.hardwaredash.app.entity

import com.hardwaredash.app.dto.ProductUnitResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.OffsetDateTime

@Document(collection = "product_unit")
data class ProductUnitEntity(
    @Id val id: String? = null,
    @Field val name: String,
    @Field val unit: String,
    @Field("multiple") val multiple: Double,
    @Field("multiple_with") val multipleWith: String? = null,
    @Field("read_only") val readOnly: Boolean,
    @Field("is_active") val isActive: Boolean,
    @Field("created_by") val createdBy: String?,
    @Field("created_date") val createdDate: OffsetDateTime?,
    @Field("modified_by") val modifiedBy: String? = null,
    @Field("modified_date") val modifiedDate: OffsetDateTime? = null

) {
    fun convertToDtoResponse(): ProductUnitResponse {
        return ProductUnitResponse(
            id = this.id,
            name = this.name,
            unit = this.unit,
            isActive = this.isActive,
            multiple = this.multiple,
            multipleWith = this.multipleWith,
            readOnly = this.readOnly
        )
    }
}
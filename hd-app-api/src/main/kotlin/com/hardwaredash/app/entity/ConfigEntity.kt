package com.hardwaredash.app.entity

import com.hardwaredash.app.dto.response.ConfigResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.OffsetDateTime


@Document(collection = "configs")
data class ConfigEntity(
    @Id val id: String? = null,
    @Field val key: String,
    @Field val value: String,
    @Field("used_for") val usedFor: String,
    @Field("is_active") val isActive: Boolean,
    @Field("created_by") val createdBy: String?,
    @Field("created_date") val createdDate: OffsetDateTime?,
    @Field("modified_by") val modifiedBy: String? = null,
    @Field("modified_date") val modifiedDate: OffsetDateTime? = null

) {
    fun convertToDtoResponse(): ConfigResponse {
        return ConfigResponse(
            id = this.id,
            key = this.key,
            value = this.value,
            isActive = this.isActive,
            usedFor = this.usedFor
        )
    }
}
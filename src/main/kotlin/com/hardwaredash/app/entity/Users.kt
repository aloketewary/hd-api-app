package com.hardwaredash.app.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Document(collection = "users")
data class UserEntity(
    @Id val id: String? = null,
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    val email: String,
    val password: String,
    @Field("fullname") val fullName: String,
    val enabled: Boolean,
    @DBRef val roles: MutableSet<RoleEntity>
)

@Document(collection = "roles")
data class RoleEntity(
    @Id var id: String? = null,
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    var name: RoleEnum
)

enum class RoleEnum {
    ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN
}
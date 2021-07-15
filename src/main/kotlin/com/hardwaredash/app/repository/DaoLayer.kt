package com.hardwaredash.app.repository

import com.hardwaredash.app.entity.*
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ConfigDao : MongoRepository<ConfigEntity, String> {
    fun findByUsedFor(usedFor: String): List<ConfigEntity>
}

@Repository
interface ProductDao : MongoRepository<ProductEntity, String>

@Repository
interface ProductUnitDao : MongoRepository<ProductUnitEntity, String>

@Repository
interface UserDao : MongoRepository<UserEntity, String> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): Optional<UserEntity>
}

@Repository
interface RoleDao : MongoRepository<RoleEntity, String> {
    fun findByName(name: RoleEnum): Optional<RoleEntity>
}
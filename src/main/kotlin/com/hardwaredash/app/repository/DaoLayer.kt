package com.hardwaredash.app.repository

import com.hardwaredash.app.entity.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ConfigDao : MongoRepository<ConfigEntity, String> {
    fun findByUsedFor(usedFor: String): List<ConfigEntity>
}

@Repository
interface ProductDao : MongoRepository<ProductEntity, String> {
    @Query(
        "{\$or:[ {'productName': /.*?0.*/}," +
                "{'productsVariants.buyPrice': /.*?0.*/}," +
                "{'productsVariants.wholeSalePrice': /.*?0.*/}," +
                "{'productsVariants.sellingPrice': /.*?0.*/}," +
                "{'productsVariants.stockTotal': /.*?0.*/}" +
                "]}"
    )
    fun searchAndFindAll(searchQuery: String, page: Pageable): Page<ProductEntity>
}

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
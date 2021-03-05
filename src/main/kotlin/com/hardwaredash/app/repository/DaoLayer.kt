package com.hardwaredash.app.repository

import com.hardwaredash.app.entity.ConfigEntity
import com.hardwaredash.app.entity.ProductEntity
import com.hardwaredash.app.entity.ProductUnitEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfigDao : MongoRepository<ConfigEntity, String> {
    fun findByUsedFor(usedFor: String): List<ConfigEntity>
}

@Repository
interface ProductDao : MongoRepository<ProductEntity, String>

@Repository
interface ProductUnitDao : MongoRepository<ProductUnitEntity, String>

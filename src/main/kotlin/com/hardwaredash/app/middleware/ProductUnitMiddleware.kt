package com.hardwaredash.app.middleware

import com.hardwaredash.app.entity.ProductUnitEntity
import com.hardwaredash.app.repository.ConfigDao
import com.hardwaredash.app.repository.ProductUnitDao
import com.hardwaredash.app.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProductUnitMiddleware(
    private val productDao: ProductUnitDao
) : BasicCrud<String, ProductUnitEntity> {

    fun getAllProductUnits(): List<ProductUnitEntity> {
        return productDao.findAll()
    }

    override fun getAll(pageable: Pageable): Page<ProductUnitEntity> {
        TODO("Not yet implemented")
    }

    override fun getById(id: String): Optional<ProductUnitEntity> {
        TODO("Not yet implemented")
    }

    override fun insert(entity: ProductUnitEntity): ProductUnitEntity {
        TODO("Not yet implemented")
    }

    override fun update(entity: ProductUnitEntity): ProductUnitEntity {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: String): Optional<ProductUnitEntity> {
        TODO("Not yet implemented")
    }

}
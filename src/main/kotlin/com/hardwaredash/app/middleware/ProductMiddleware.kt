package com.hardwaredash.app.middleware

import com.hardwaredash.app.entity.ProductEntity
import com.hardwaredash.app.repository.ProductDao
import com.hardwaredash.app.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductMiddleware(
    private val productDao: ProductDao
) : BasicCrud<String, ProductEntity> {
    fun getAllProducts(): List<ProductEntity> {
        val productList = productDao.findAll()
        return productList.filter { it.isActive }.toList()
    }

    override fun getAll(pageable: Pageable): Page<ProductEntity> = productDao.findAll(pageable)

    fun getAllByIds(ids: List<String>): MutableIterable<ProductEntity> {
        return productDao.findAllById(ids)
    }

    override fun getById(id: String): Optional<ProductEntity> {
        return productDao.findById(id)
    }

    override fun insert(entity: ProductEntity): ProductEntity {
        return productDao.save(entity)
    }

    override fun update(entity: ProductEntity): ProductEntity {
        return productDao.save(entity)
    }

    override fun deleteById(id: String): Optional<ProductEntity> {
        val productEntity = getById(id)
        if (productEntity.isPresent) {
            productDao.delete(productEntity.get())
        }
        return productEntity
    }

    fun insertAll(entityList: List<ProductEntity>): List<ProductEntity> {
        return productDao.saveAll(entityList)
    }

    fun deleteAllById(ids: List<String>): List<ProductEntity> {
        val listOfProductEntity = getAllByIds(ids)
        if (listOfProductEntity.toList().isNotEmpty()) {
            productDao.deleteAll(listOfProductEntity)
        }
        return listOfProductEntity.toList()
    }
}
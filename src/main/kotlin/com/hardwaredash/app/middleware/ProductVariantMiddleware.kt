package com.hardwaredash.app.middleware

import com.hardwaredash.app.entity.ProductVariants
import com.hardwaredash.app.repository.ProductVariantDao
import com.hardwaredash.app.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductVariantMiddleware(
    private val productVariantDao: ProductVariantDao
) : BasicCrud<String, ProductVariants> {
    fun getAllProducts(): List<ProductVariants> {
        val productList = productVariantDao.findAll()
        return productList.filter { it.isActive }.toList()
    }

    override fun getAll(pageable: Pageable): Page<ProductVariants> = productVariantDao.findAll(pageable)

    fun getAllByIds(ids: List<String>): MutableIterable<ProductVariants> {
        return productVariantDao.findAllById(ids)
    }

    override fun getById(id: String): Optional<ProductVariants> {
        return productVariantDao.findById(id)
    }

    override fun insert(entity: ProductVariants): ProductVariants {
        return productVariantDao.save(entity)
    }

    override fun update(entity: ProductVariants): ProductVariants {
        return productVariantDao.save(entity)
    }

    override fun deleteById(id: String): Optional<ProductVariants> {
        val productEntity = getById(id)
        if (productEntity.isPresent) {
            productVariantDao.delete(productEntity.get())
        }
        return productEntity
    }

    fun insertAll(entityList: List<ProductVariants>): List<ProductVariants> {
        return productVariantDao.saveAll(entityList)
    }

    fun deleteAllById(ids: List<String>): List<ProductVariants> {
        val listOfProductEntity = getAllByIds(ids)
        if (listOfProductEntity.toList().isNotEmpty()) {
            productVariantDao.deleteAll(listOfProductEntity)
        }
        return listOfProductEntity.toList()
    }
}
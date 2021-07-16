package com.hardwaredash.app.middleware

import com.hardwaredash.app.entity.ConfigEntity
import com.hardwaredash.app.entity.ProductEntity
import com.hardwaredash.app.repository.ConfigDao
import com.hardwaredash.app.repository.ProductUnitDao
import com.hardwaredash.app.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ConfigMiddleware(
    private val configDao: ConfigDao,
    private val productUnitDao: ProductUnitDao,
) : BasicCrud<String, ConfigEntity> {
    fun getAllConfig(usedFor: String): Map<String, Any> {
        val configMap = mutableMapOf<String, Any>()
        val configList = configDao.findAll()
        val unitList = productUnitDao.findAll()
        val configMapValue = configList.filter { it.isActive }.associate { it.key to it.value }
        val unitMapValue = unitList.filter { it.isActive }.map { it.convertToDtoResponse() }
        configMap.putAll(configMapValue)
        configMap["UNITS"] = unitMapValue
        return configMap
    }

    override fun getAll(pageable: Pageable): Page<ConfigEntity> {
        return configDao.findAll(pageable)
    }

    override fun getById(id: String): Optional<ConfigEntity> {
        return configDao.findById(id)
    }

    override fun insert(entity: ConfigEntity): ConfigEntity {
        return configDao.save(entity)
    }

    override fun update(entity: ConfigEntity): ConfigEntity {
        return configDao.save(entity)
    }

    override fun deleteById(id: String): Optional<ConfigEntity> {
        val configEntity = getById(id)
        if (configEntity.isPresent) {
            configDao.delete(configEntity.get())
        }
        return configEntity
    }

    fun getAllConfigForAdmin(usedFor: String): List<ConfigEntity> {
        val configList = configDao.findAll()
        return configList.filter { it.isActive }
    }
}
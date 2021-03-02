package com.hardwaredash.app.middleware

import com.hardwaredash.app.entity.ConfigEntity
import com.hardwaredash.app.enums.DashboardContent
import com.hardwaredash.app.repository.ConfigDao
import com.hardwaredash.app.repository.ProductDao
import com.hardwaredash.app.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class DashboardMiddleware(
    private val configDao: ConfigDao,
    private val productDao: ProductDao
) : BasicCrud<String, ConfigEntity> {

    fun getDashboardInfo(): Map<String, String> {
        val configList = configDao.findAll()
        val productList = productDao.findAll()
        val dashboardMap = mutableMapOf<String, String>()
        dashboardMap[DashboardContent.CONFIG.name] = configList.size.toString()
        dashboardMap[DashboardContent.PRODUCT.name] = productList.size.toString()
        return dashboardMap
    }

    override fun getAll(pageable: Pageable): Page<ConfigEntity> {
        TODO("Not yet implemented")
    }

    override fun getById(id: String): Optional<ConfigEntity> {
        TODO("Not yet implemented")
    }

    override fun insert(entity: ConfigEntity): ConfigEntity {
        TODO("Not yet implemented")
    }

    override fun update(entity: ConfigEntity): ConfigEntity {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: String): Optional<ConfigEntity> {
        TODO("Not yet implemented")
    }

}
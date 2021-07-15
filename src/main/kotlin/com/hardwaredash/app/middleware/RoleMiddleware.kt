package com.hardwaredash.app.middleware

import com.hardwaredash.app.entity.RoleEntity
import com.hardwaredash.app.entity.RoleEnum
import com.hardwaredash.app.repository.RoleDao
import com.hardwaredash.app.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class RoleMiddleware(
    private val roleDao: RoleDao
) : BasicCrud<String, RoleEntity> {

    override fun getAll(pageable: Pageable): Page<RoleEntity> = roleDao.findAll(pageable)

    override fun getById(id: String): Optional<RoleEntity> = roleDao.findById(id)

    override fun insert(entity: RoleEntity): RoleEntity = roleDao.insert(entity)

    override fun update(entity: RoleEntity): RoleEntity = roleDao.save(entity)

    override fun deleteById(id: String): Optional<RoleEntity> {
        val entity = getById(id)
        if (entity.isPresent) {
            roleDao.delete(entity.get())
        }
        return entity
    }

    fun findByName(name: RoleEnum): Optional<RoleEntity> = roleDao.findByName(name)

    fun findAll(): List<RoleEntity> = roleDao.findAll()

    fun saveAll(listOfEntity: List<RoleEntity>): List<RoleEntity> = roleDao.saveAll(listOfEntity)
}
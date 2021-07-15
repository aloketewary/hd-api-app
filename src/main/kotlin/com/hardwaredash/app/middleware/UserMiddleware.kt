package com.hardwaredash.app.middleware

import com.hardwaredash.app.entity.ProductUnitEntity
import com.hardwaredash.app.entity.UserEntity
import com.hardwaredash.app.repository.ConfigDao
import com.hardwaredash.app.repository.ProductUnitDao
import com.hardwaredash.app.repository.UserDao
import com.hardwaredash.app.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserMiddleware(
    private val userDao: UserDao
) : BasicCrud<String, UserEntity> {

    override fun getAll(pageable: Pageable): Page<UserEntity> = userDao.findAll(pageable)

    override fun getById(id: String): Optional<UserEntity> = userDao.findById(id)

    override fun insert(entity: UserEntity): UserEntity = userDao.insert(entity)

    override fun update(entity: UserEntity): UserEntity = userDao.save(entity)

    override fun deleteById(id: String): Optional<UserEntity> {
        val entity = getById(id)
        if (entity.isPresent) {
            userDao.delete(entity.get())
        }
        return entity
    }


    fun existsByEmail(email: String): Boolean = userDao.existsByEmail(email)

    fun findByEmail(email: String): Optional<UserEntity> = userDao.findByEmail(email)

}
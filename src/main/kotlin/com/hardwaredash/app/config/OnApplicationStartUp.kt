package com.hardwaredash.app.config

import com.hardwaredash.app.entity.RoleEntity
import com.hardwaredash.app.entity.RoleEnum
import com.hardwaredash.app.middleware.RoleMiddleware
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@Component
class OnApplicationStartUp(
    val roleRepository: RoleMiddleware
) {
    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent) {
        //Write your business logic here.
        if (roleRepository.findAll().isEmpty()) {
            loadDataToDB()
        }
    }

    fun loadDataToDB() {
        val roleMap = RoleEnum.values().map { RoleEntity(name = it) }
        roleRepository.saveAll(roleMap)
    }
}
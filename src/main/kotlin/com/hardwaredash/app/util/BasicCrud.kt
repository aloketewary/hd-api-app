package com.hardwaredash.app.util

import com.hardwaredash.app.entity.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface BasicCrud<K, T> {
    fun getAll(pageable: Pageable): Page<T>
    fun getById(id: K): Optional<T>
    fun insert(entity: T): T
    fun update(entity: T): T
    fun deleteById(id: K): Optional<T>
}


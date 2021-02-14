package com.hardwaredash.app.entity

import java.time.LocalDate
import java.time.OffsetDateTime

open class BaseEntity(
    open var createdBy: String? = null,
    open var createdDate: OffsetDateTime? = null,
    open var modifiedBy: String? = null,
    open var modifiedDate: LocalDate? = null,
)
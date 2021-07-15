package com.hardwaredash.app.util

import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

interface DateTimeProvider {
   fun now(): Instant
   fun nowInDate(): Date
   fun nowInLocalDateTime(): LocalDateTime
}

@Component
class DateTimeProviderImpl: DateTimeProvider {
    override fun now(): Instant = Instant.now()

    override fun nowInDate(): Date = Date()

    override fun nowInLocalDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }

}
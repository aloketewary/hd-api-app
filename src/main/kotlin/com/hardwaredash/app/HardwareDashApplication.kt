package com.hardwaredash.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@ComponentScan(basePackages = ["com.hardwaredash.app"])
@EnableCaching(proxyTargetClass = true)
class HardwareDashApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<HardwareDashApplication>(*args)
        }

    }
}



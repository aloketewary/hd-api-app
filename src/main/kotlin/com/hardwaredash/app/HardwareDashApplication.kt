package com.hardwaredash.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@SpringBootApplication
@ComponentScan(basePackages = ["com.hardwaredash.app"])
class HardwareDashApplication {

    @RequestMapping("/")
    @ResponseBody
    fun home(): String {
        return "Hello World!"
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<HardwareDashApplication>(*args)
        }

    }
}



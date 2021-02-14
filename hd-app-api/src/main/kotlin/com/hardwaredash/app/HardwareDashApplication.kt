package com.hardwaredash.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.hardwaredash.app"])
class HardwareDashApplication

fun main(args: Array<String>) {
	runApplication<HardwareDashApplication>(*args)
}

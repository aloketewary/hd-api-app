package com.hardwaredash.app.controller

import com.hardwaredash.app.dto.request.ConfigRequest
import com.hardwaredash.app.service.ConfigService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/api")
@Api(value = "ConfigApiService", tags = ["ConfigApi"])
class ConfigController(
    val configService: ConfigService
) {

    @ApiOperation(value = "Get Application level config")
    @GetMapping("/v1/config")
    fun getConfig() = configService.getAllConfig("ALL")

    @ApiOperation(value = "Get admin level config")
    @GetMapping("/v1/config/admin/{used_for}")
    fun getConfigUsedFor(@PathVariable("used_for") usedFor: String) = configService.getAllConfigForAdmin(usedFor)

    @ApiOperation(value = "Insert new config")
    @PostMapping("/v1/config/admin")
    fun addNewConfig(@RequestBody config: ConfigRequest) = configService.insert(config)

    @ApiOperation(value = "Update config")
    @PatchMapping("/v1/config/admin/{id}")
    fun updateConfig(@PathVariable id: String, @RequestBody config: ConfigRequest) = configService.update(id, config)

    @ApiOperation(value = "Delete config")
    @DeleteMapping("/v1/config/admin/{id}")
    fun deleteConfig(@PathVariable id: String) = configService.delete(id)

}
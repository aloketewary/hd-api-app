package com.hardwaredash.app.controller

import com.hardwaredash.app.dto.request.ConfigRequest
import com.hardwaredash.app.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/api")
@Api(value = "UserApiService", tags = ["UserApi"])
class UserController(
    val userService: UserService
) {

    @ApiOperation(value = "Get Application level config")
    @GetMapping("/v1/users")
    fun getAllUsers() = userService.getAllConfig("ALL")

    @ApiOperation(value = "Insert new config")
    @PostMapping("/v1/user")
    fun addNewConfig(@RequestBody config: ConfigRequest) = userService.insert(config)

    @ApiOperation(value = "Update config")
    @PatchMapping("/v1/user/{id}")
    fun updateConfig(@PathVariable id: String, @RequestBody config: ConfigRequest) = userService.update(id, config)

    @ApiOperation(value = "Delete config")
    @DeleteMapping("/v1/user/{id}")
    fun deleteConfig(@PathVariable id: String) = userService.delete(id)

}
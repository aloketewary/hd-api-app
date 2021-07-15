package com.hardwaredash.app.controller

import com.hardwaredash.app.dto.ConfigRequest
import com.hardwaredash.app.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/api")
@Api(value = "UserApiService", tags = ["UserApi"])
@PreAuthorize("hasRole('ADMIN')")
class UserController(
    private val userService: UserService
) {

    @ApiOperation(value = "Get Application level config")
    @GetMapping("/v1/users")
    fun getAllUsers(@RequestParam(name = "page", defaultValue = "0") page: Int,
                    @RequestParam(name = "size", defaultValue = "10") size: Int) = userService.getAllUsersForAdmin(page, size)

    @ApiOperation(value = "Insert new config")
    @PostMapping("/v1/user")
    fun addNewConfig(@RequestBody config: ConfigRequest) = userService.addNewUser(config)

    @ApiOperation(value = "Update config")
    @PatchMapping("/v1/user/{id}")
    fun updateConfig(@PathVariable id: String, @RequestBody config: ConfigRequest) = userService.updateUser(id, config)

    @ApiOperation(value = "Delete config")
    @DeleteMapping("/v1/user/{id}")
    fun deleteConfig(@PathVariable id: String) = userService.deleteUser(id)

}
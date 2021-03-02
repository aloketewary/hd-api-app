package com.hardwaredash.app.controller

import com.hardwaredash.app.service.DashboardService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api")
@Api(value = "DashboardApiService", tags = ["DashboardApi"])
class DashboardController(
    val dashboardService: DashboardService
) {

    @ApiOperation(value = "Get Dashboard Info")
    @GetMapping("/v1/dashboard/info")
    fun getDashboardInfo() = dashboardService.getDashboardInfo()
}
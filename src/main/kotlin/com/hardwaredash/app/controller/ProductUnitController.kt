package com.hardwaredash.app.controller

import com.hardwaredash.app.dto.ProductUnitRequest
import com.hardwaredash.app.service.ProductUnitService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api")
@Api(value = "ProductUnitApiService", tags = ["ProductUnitApi"])
class ProductUnitController(
    val productUnitService: ProductUnitService
) {

    @ApiOperation(value = "Get Product Unit list")
    @GetMapping("/v1/product/unit")
    fun getAllProductUnits() = productUnitService.getAllProductUnits()

    @ApiOperation(value = "Insert new Product Unit")
    @PostMapping("/v1/product/unit")
    fun insertNewProductUnit(productUnitReq: ProductUnitRequest) = productUnitService.insertProductUnit(productUnitReq)
}
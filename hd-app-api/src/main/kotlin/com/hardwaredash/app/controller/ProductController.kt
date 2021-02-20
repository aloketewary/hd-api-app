package com.hardwaredash.app.controller

import com.hardwaredash.app.dto.request.ProductRequest
import com.hardwaredash.app.service.ProductService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController()
@RequestMapping("/api")
@Api(value = "ProductApiService", tags = ["ProductApi"])
class ProductController(
    private val productService: ProductService
) {

    @ApiOperation(value = "Get all Products")
    @GetMapping("/v1/products")
    fun getAllProducts() = productService.getAllProducts()

    @ApiOperation(value = "Insert new Product")
    @PostMapping("/v1/product")
    fun addNewProduct(@RequestBody product: ProductRequest) = productService.insert(product)

//    @ApiOperation(value = "Update config")
//    @PatchMapping("/v1/product/{id}")
//    fun updateConfig(@PathVariable id: String, @RequestBody config: ConfigRequest) = productService.update(id, config)
//
//    @ApiOperation(value = "Delete config")
//    @DeleteMapping("/v1/product/{id}")
//    fun deleteConfig(@PathVariable id: String) = productService.delete(id)

    @ApiOperation(value = "Insert new Product")
    @PostMapping("/v1/product/bulk")
    fun addBulkProduct(@RequestParam("file") file: MultipartFile) = productService.insertBulkProduct(file)

}
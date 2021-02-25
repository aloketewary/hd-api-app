package com.hardwaredash.app.controller

import com.hardwaredash.app.dto.request.ProductRequest
import com.hardwaredash.app.service.ProductService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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

    @ApiOperation(value = "Update product")
    @PatchMapping("/v1/product/{id}")
    fun updateProduct(@PathVariable id: String, @RequestBody product: ProductRequest) = productService.update(id, product)

    @ApiOperation(value = "Delete product")
    @DeleteMapping("/v1/product/{id}")
    fun deleteProduct(@PathVariable id: String) = productService.delete(id)

    @ApiOperation(value = "Insert new Product")
    @PostMapping("/v1/product/bulk")
    fun addBulkProduct(@RequestParam("file") file: MultipartFile) = productService.insertBulkProduct(file)

    @ApiOperation(value = "Delete List of product")
    @DeleteMapping("/v1/product/bulk/{ids}")
    fun deleteProducts(@PathVariable ids: String) = productService.deleteAllById(ids)
}
package com.hardwaredash.app.service

import com.hardwaredash.app.dto.request.ProductRequest
import com.hardwaredash.app.dto.request.ProductVariantRequest
import com.hardwaredash.app.dto.response.ProductResponse
import com.hardwaredash.app.entity.ProductEntity
import com.hardwaredash.app.entity.ProductVariants
import com.hardwaredash.app.middleware.ProductMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.util.convertToFile
import com.hardwaredash.app.util.httpCommonError
import com.hardwaredash.app.util.httpGetSuccess
import com.hardwaredash.app.util.httpPostSuccess
import mu.KotlinLogging
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileInputStream
import java.time.OffsetDateTime

private val logger = KotlinLogging.logger {}

@Service
class ProductService(
    private val productMiddleware: ProductMiddleware
) {

    fun getAllProducts(): CommonHttpResponse<List<ProductResponse>> {
        logger.info { "Get all products" }
        return try {
            val allProducts = productMiddleware.getAllProducts()
            val listOfProductResponse = allProducts.map { it.convertToResponse() }
            httpGetSuccess(listOfProductResponse)
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    fun insert(product: ProductRequest): CommonHttpResponse<ProductResponse> {
        logger.info { "Insert new product where data = $product" }
        return try {
            val productEntity = generateProductEntity(product)
            val savedProduct = productMiddleware.insert(productEntity)
            val productResponse = savedProduct.convertToResponse()
            httpPostSuccess(productResponse, "Product Created success")
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    fun insertBulkProduct(multipartFile: MultipartFile): CommonHttpResponse<String> {
        logger.info { "Insert new product where data = ${multipartFile.name}" }
        return try {
            val excelFile = FileInputStream(multipartFile.convertToFile(multipartFile.name))
            excelFile.use { excel ->
                val workbook = XSSFWorkbook(excel)
                workbook.use { book ->
                    val sheet = book.getSheet("Products")
                    val lastRowNum = sheet.lastRowNum
                    val extractedProductList = mutableListOf<ProductRequest>()
                    for (i in 1..lastRowNum) {
                        val currentRow = sheet.getRow(i)
                        val lastCellNum = currentRow.lastCellNum
                        var productName = ""
                        var variant = ""
                        var variantName = ""
                        var parentId = ""
                        var buyPrice = 0.0
                        var stockTotal = 0
                        var onSale = false
                        var onSalePrice = 0.0
                        var wholeSalePrice = 0.0
                        var sellingPrice = 0.0
                        for (j in 1..lastCellNum) {
                            when (j) {
                                1 -> productName = currentRow.getCell(j).toString()
                                2 -> variant = currentRow.getCell(j).toString()
                                3 -> variantName = currentRow.getCell(j).toString()
                                4 -> parentId = currentRow.getCell(j).toString().toDouble().toInt().toString()
                                5 -> buyPrice = currentRow.getCell(j).toString().toDouble()
                                6 -> stockTotal = currentRow.getCell(j).toString().toDouble().toInt()
                                7 -> onSale = currentRow.getCell(j).toString().toBoolean()
                                8 -> onSalePrice = currentRow.getCell(j).toString().toDouble()
                                9 -> wholeSalePrice = currentRow.getCell(j).toString().toDouble()
                                10 -> sellingPrice = currentRow.getCell(j).toString().toDouble()
                            }
                        }
                        val productVariantRequest = ProductVariantRequest(
                            variant = variant,
                            isActive = true,
                            onSalePrice = onSalePrice,
                            onSale = onSale,
                            buyPrice = buyPrice,
                            parentId = parentId,
                            stockTotal = stockTotal,
                            variantName = variantName,
                            wholeSalePrice = wholeSalePrice,
                            sellingPrice = sellingPrice
                        )
                        extractedProductList.add(
                            ProductRequest(
                                productName = productName,
                                productVariantRequest = productVariantRequest,
                                isActive = true
                            )
                        )
                    }
                    val entityList = extractedProductList.map { generateProductEntity(it) }
                    productMiddleware.insertAll(entityList)
                    httpPostSuccess("${extractedProductList.size} Product(s) inserted", "Bulk upload success")
                }
            }
        } catch (e: Exception) {
            httpCommonError(e)
        }
    }


    private fun generateProductEntity(product: ProductRequest): ProductEntity {
        val productVariants = ProductVariants(
            parentId = product.productVariantRequest.parentId,
            variant = product.productVariantRequest.variant,
            variantName = product.productVariantRequest.variantName,
            stockTotal = product.productVariantRequest.stockTotal,
            buyPrice = product.productVariantRequest.buyPrice,
            wholeSalePrice = product.productVariantRequest.wholeSalePrice,
            onSale = product.productVariantRequest.onSale,
            onSalePrice = product.productVariantRequest.onSalePrice,
            isActive = product.productVariantRequest.isActive,
            createdBy = "ADMIN",
            createdDate = OffsetDateTime.now(),
            sellingPrice = product.productVariantRequest.sellingPrice,
        )
        return ProductEntity(
            productName = product.productName,
            productVariants = productVariants,
            isActive = product.isActive,
            createdBy = "ADMIN",
            createdDate = OffsetDateTime.now(),
        )
    }

}
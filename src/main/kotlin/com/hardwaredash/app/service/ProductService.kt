package com.hardwaredash.app.service

import com.hardwaredash.app.dto.request.ProductRequest
import com.hardwaredash.app.dto.request.ProductVariantRequest
import com.hardwaredash.app.dto.response.ConfigResponse
import com.hardwaredash.app.dto.response.ProductResponse
import com.hardwaredash.app.entity.ProductEntity
import com.hardwaredash.app.entity.ProductVariants
import com.hardwaredash.app.middleware.ProductMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.util.*
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
                                productVariant = productVariantRequest,
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
            parentId = product.productVariant.parentId,
            variant = product.productVariant.variant,
            variantName = product.productVariant.variantName,
            stockTotal = product.productVariant.stockTotal,
            buyPrice = product.productVariant.buyPrice,
            wholeSalePrice = product.productVariant.wholeSalePrice,
            onSale = product.productVariant.onSale,
            onSalePrice = product.productVariant.onSalePrice,
            isActive = product.productVariant.isActive,
            createdBy = "ADMIN",
            createdDate = OffsetDateTime.now(),
            sellingPrice = product.productVariant.sellingPrice,
        )
        return ProductEntity(
            productName = product.productName,
            productVariants = productVariants,
            isActive = product.isActive,
            createdBy = "ADMIN",
            createdDate = OffsetDateTime.now(),
        )
    }

    fun update(id: String, product: ProductRequest): CommonHttpResponse<ProductResponse> {
        logger.info { "Update existing config id = $id where data = $product" }
        return try {
            val productById = productMiddleware.getById(id)
            when {
                productById.isPresent -> {
                    val productEntity = ProductEntity(
                        id = productById.get().id,
                        productName = product.productName,
                        productVariants = ProductVariants(
                            id = productById.get().productVariants.id,
                            isActive = product.isActive,
                            wholeSalePrice = product.productVariant.wholeSalePrice,
                            variantName = product.productVariant.variantName,
                            stockTotal = product.productVariant.stockTotal,
                            parentId = product.productVariant.parentId,
                            buyPrice = product.productVariant.buyPrice,
                            onSale = product.productVariant.onSale,
                            onSalePrice = product.productVariant.onSalePrice,
                            sellingPrice = product.productVariant.sellingPrice,
                            variant = product.productVariant.variant,
                            createdBy = productById.get().productVariants.createdBy,
                            createdDate = productById.get().productVariants.createdDate,
                            modifiedBy = "ADMIN",
                            modifiedDate = OffsetDateTime.now()
                        ),
                        isActive = product.isActive,
                        createdBy = productById.get().createdBy,
                        createdDate = productById.get().createdDate,
                        modifiedBy = "ADMIN",
                        modifiedDate = OffsetDateTime.now()
                    )
                    val updatedProduct = productMiddleware.update(productEntity)
                    httpPatchSuccess(updatedProduct.convertToResponse(), "Config updated success")
                }
                else -> httpCommonError(Exception("No Config found with id=$id"))
            }
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    fun delete(id: String): CommonHttpResponse<ProductResponse> {
        logger.info { "Delete existing config where id = $id" }
        return try {
            val deleteById = productMiddleware.deleteById(id)
            if (deleteById.isPresent) {
                httpDeleteSuccess(deleteById.get().convertToResponse())
            } else {
                httpCommonError(Exception("Delete Failure for id=$id"))
            }
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

    fun deleteAllById(ids: String): CommonHttpResponse<List<ProductResponse>> {
        logger.info { "Delete existing config where id = $ids" }
        return try {
            val idList = ids.split(",")
            val deleteByIds = productMiddleware.deleteAllById(idList)
            if (deleteByIds.isNotEmpty()) {
                httpDeleteSuccess(deleteByIds.map { it.convertToResponse() })
            } else {
                httpCommonError(Exception("Delete Failure for id=$ids"))
            }
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }

}
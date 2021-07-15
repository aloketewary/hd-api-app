package com.hardwaredash.app.service.impl

import com.hardwaredash.app.dto.ProductRequest
import com.hardwaredash.app.dto.ProductResponse
import com.hardwaredash.app.dto.ProductVariantRequest
import com.hardwaredash.app.entity.ProductEntity
import com.hardwaredash.app.entity.ProductVariants
import com.hardwaredash.app.middleware.ProductMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.service.ProductService
import com.hardwaredash.app.util.*
import mu.KotlinLogging
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileInputStream
import java.time.OffsetDateTime

private val logger = KotlinLogging.logger {}

@Service
class ProductServiceImpl(
    private val productMiddleware: ProductMiddleware
): ProductService {

    override fun getAllProducts(): CommonHttpResponse<List<ProductResponse>> {
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

    override fun insert(product: ProductRequest): CommonHttpResponse<ProductResponse> {
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

    override fun insertBulkProduct(multipartFile: MultipartFile): CommonHttpResponse<String> {
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
                        var unit = ""
                        var multiText = ""
                        for (j in 1..lastCellNum) {
                            when (j) {
                                1 -> productName = currentRow.getCell(j).toString()
                                2 -> variant = currentRow.getCell(j).toString()
                                3 -> variantName = currentRow.getCell(j).toString()
                                4 -> parentId = currentRow.getCell(j).toString().toDouble().toInt().toString()
                                5 -> buyPrice = currentRow.getCell(j).toString().toDouble()
                                6 -> unit = currentRow.getCell(j).toString()
                                7 -> stockTotal = currentRow.getCell(j).toString().toDouble().toInt()
                                8 -> onSale = currentRow.getCell(j).toString().toBoolean()
                                9 -> onSalePrice = currentRow.getCell(j).toString().toDouble()
                                10 -> wholeSalePrice = currentRow.getCell(j).toString().toDouble()
                                11 -> sellingPrice = currentRow.getCell(j).toString().toDouble()
                                12 -> multiText = currentRow.getCell(j).toString()
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
                            sellingPrice = sellingPrice,
                            unit = unit,
                            multiText = multiText
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
            unit = product.productVariant.unit,
            multiText = product.productVariant.multiText,
        )
        return ProductEntity(
            productName = product.productName,
            productVariants = productVariants,
            isActive = product.isActive,
            createdBy = "ADMIN",
            createdDate = OffsetDateTime.now(),
        )
    }

    override fun update(id: String, product: ProductRequest): CommonHttpResponse<ProductResponse> {
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
                            modifiedDate = OffsetDateTime.now(),
                            unit = productById.get().productVariants.unit,
                            multiText = productById.get().productVariants.multiText
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

    override fun delete(id: String): CommonHttpResponse<ProductResponse> {
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

    override fun deleteAllById(ids: String): CommonHttpResponse<List<ProductResponse>> {
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
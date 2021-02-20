package com.hardwaredash.app.util

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate

fun String.toLocalDate() = LocalDate.parse(this, Objects.dateFormat)

fun MultipartFile.convertToFile(fileName: String): File {
    val convFile = File(System.getProperty("java.io.tmpdir") + "/" + fileName)
    this.transferTo(convFile)
    return convFile
}
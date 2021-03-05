package com.hardwaredash.app.service

import com.hardwaredash.app.middleware.DashboardMiddleware
import com.hardwaredash.app.model.CommonHttpResponse
import com.hardwaredash.app.util.httpCommonError
import com.hardwaredash.app.util.httpGetSuccess
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class DashboardService(
    private val dashboardMiddleware: DashboardMiddleware
) {

    /**
     * Get Dashboard Info
     */
    fun getDashboardInfo(): CommonHttpResponse<Map<String, String>> {
        logger.info { "Get Dashboard Info" }
        return try {
            val allProducts = dashboardMiddleware.getDashboardInfo()
            httpGetSuccess(allProducts)
        } catch (e: Exception) {
            logger.error { e }
            httpCommonError(e)
        }
    }
}
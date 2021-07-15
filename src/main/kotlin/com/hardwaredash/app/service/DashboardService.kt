package com.hardwaredash.app.service

import com.hardwaredash.app.model.CommonHttpResponse


interface DashboardService {

    /**
     * Get Dashboard Info
     */
    fun getDashboardInfo(): CommonHttpResponse<Map<String, String>>
}
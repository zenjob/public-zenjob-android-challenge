package com.zenjob.android.browsr.data

import com.squareup.moshi.Json

data class PaginatedListResponse<T>(
    val page: Int,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int,
    val results: List<T>
)

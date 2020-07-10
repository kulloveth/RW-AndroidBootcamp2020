package com.kulloveth.covid19virustracker.model

data class BaseResponse(val data : Data)
data class Data(

    val paginationMeta: PaginationMeta,
    val last_update: String,
    val rows: List<CountryStatus>
)

data class CountryStatus(
    val country: String,
    val country_abbreviation: String,
    val total_cases: String,
    val new_cases: String,
    val total_deaths: String,
    val new_deaths: Int,
    val total_recovered: String,
    val active_cases: String,
    val serious_critical: String,
    val cases_per_mill_pop: String,
    val flag: String
)

data class PaginationMeta(

    val currentPage: Int,
    val currentPageSize: Int,
    val totalPages: Int,
    val totalRecords: Int
)
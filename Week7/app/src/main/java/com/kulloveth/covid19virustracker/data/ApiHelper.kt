package com.kulloveth.covid19virustracker.data

import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.utils.Resource

interface ApiHelper {
    suspend fun getStatusByCountry(): Resource<List<CountryStatus>>
}
package com.example.vimos.catalog.api

import com.example.vimos.Constants.CATALOG_URL
import retrofit2.Response
import retrofit2.http.GET

interface CatalogApiService {
    @GET(CATALOG_URL)
    suspend fun getCatalogData() : Response<List<CategoryData>>
}
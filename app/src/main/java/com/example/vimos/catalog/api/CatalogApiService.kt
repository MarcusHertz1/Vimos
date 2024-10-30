package com.example.vimos.catalog.api

import com.example.vimos.Constants.CATALOG_URL
import com.example.vimos.Constants.PRODUCT_URL
import com.example.vimos.Constants.SLUGS_URL_END
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApiService {
    @GET(CATALOG_URL)
    suspend fun getCatalogData() : Response<List<CategoryData>>
    @GET("$PRODUCT_URL{slug}$SLUGS_URL_END")
    suspend fun getSlugsData(@Path("slug") slug: String) : Response<List<SlugElement>>
    @GET("$PRODUCT_URL{slug}")
    suspend fun getProductData(@Path("slug") slug: String) : Response<List<ProductElement>>
}
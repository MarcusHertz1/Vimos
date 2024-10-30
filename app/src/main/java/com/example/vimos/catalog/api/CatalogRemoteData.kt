package com.example.vimos.catalog.api

import javax.inject.Inject

class CatalogRemoteData @Inject constructor(private val mainService : CatalogApiService) {

    suspend fun getCatalog() = mainService.getCatalogData()
    suspend fun getSlugsData(slug: String) = mainService.getSlugsData(slug)
    suspend fun getProductData(slug: String) = mainService.getProductData(slug)
}
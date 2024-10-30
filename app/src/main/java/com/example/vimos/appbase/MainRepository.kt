package com.example.vimos.appbase

import com.example.vimos.catalog.api.CatalogRemoteData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val remoteData : CatalogRemoteData
) {
    suspend fun getCatalog() = remoteData.getCatalog()
}
package com.example.vimos.catalog.api

import com.example.vimos.Constants.MAIN_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatalogModule {
    @Provides
    fun providesBaseUrl() : String = MAIN_URL

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideMainService(retrofit : Retrofit) : CatalogApiService = retrofit.create(
        CatalogApiService::class.java)

    @Provides
    @Singleton
    fun provideCatalogRemoteData(mainService : CatalogApiService) : CatalogRemoteData = CatalogRemoteData(mainService)
}
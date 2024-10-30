package com.example.vimos.productCatalog

import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository

internal class ProductCatalogViewModel(
    private val repository: MainRepository
) : BaseViewModel<ProductCatalogUiState>() {
    override fun createInitialState() = ProductCatalogUiState()



}
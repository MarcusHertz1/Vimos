package com.example.vimos.productCatalog

import androidx.compose.runtime.Immutable
import com.example.vimos.appbase.UiState
import com.example.vimos.catalog.api.SlugElement

@Immutable
data class ProductCatalogUiState (
    val slugList: List<SlugElement> = emptyList(),
    val data: List<Product> = emptyList(),
) : UiState

data class Product(
    val iconUrl: String = "",
    val discount: String = "",
    val sku: String = "",
    val title: String = "",
    val price: String = "",
    val oldPrice: String = "",
    val units: String = ""
)
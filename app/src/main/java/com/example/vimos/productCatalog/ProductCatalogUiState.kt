package com.example.vimos.productCatalog

import androidx.compose.runtime.Immutable
import com.example.vimos.appbase.UiState

@Immutable
data class ProductCatalogUiState (
    val data: List<Product> = emptyList(),
    val topBarTitle:String = "",
) : UiState

data class Product(
    val iconUrl: String = "",
    val discount: String = "",
    val sku: String = "",
    val title: String = "",
    val price: String = "",
    val oldPrice: String = "",
    val units: String = "",
    val slug: String = "",
)
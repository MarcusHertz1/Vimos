package com.example.vimos.productCard

import androidx.compose.runtime.Immutable
import com.example.vimos.appbase.UiState

@Immutable
data class ProductCardUiState (
    val data: Product = Product(),
) : UiState

data class Product(
    val images: List<String> = emptyList(),
    val discount: String = "",
    val sku: String = "",
    val title: String = "",
    val price: String = "",
    val oldPrice: String = "",
    val units: String = "",
    val slug: String = "",
)
package com.example.vimos.productCard

import androidx.compose.runtime.Immutable
import com.example.vimos.appbase.UiState

@Immutable
internal data class ProductCardUiState (
    val data: List<CatalogItem> = emptyList(),
    val topBarTitle: String = "",
    val depth: Int = 0,
) : UiState {
    data class CatalogItem(
        val id: Int,
        val iconUrl: String,
        val title: String,
    )
}
package com.example.vimos.catalog

import androidx.compose.runtime.Immutable
import com.example.vimos.appbase.UiState
import com.example.vimos.catalog.api.CategoryData

@Immutable
data class CatalogUiState(
    val dataFromServer: List<CategoryData> = emptyList(),
    val showingData: List<CatalogItem> = emptyList(),
    val topBarTitle: String = "",
    val depthIndexList: List<Int> = emptyList(),
) : UiState

data class CatalogItem(
    val id: Int?,
    val iconUrl: String,
    val title: String,
)
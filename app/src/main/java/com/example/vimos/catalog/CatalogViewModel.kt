package com.example.vimos.catalog

import com.example.vimos.appbase.BaseViewModel

internal class CatalogViewModel : BaseViewModel<CatalogUiState>() {
    override fun createInitialState() = CatalogUiState()

}
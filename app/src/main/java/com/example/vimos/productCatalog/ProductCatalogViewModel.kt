package com.example.vimos.productCatalog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository
import kotlinx.coroutines.launch

internal class ProductCatalogViewModel(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductCatalogUiState>() {
    override fun createInitialState() = ProductCatalogUiState()

    init {
        savedStateHandle.get<String>("slug")?.let {
            viewModelScope.launch {
                println("slug: $it")
                repository.getSlugsData(it)
            }
        }
    }

}
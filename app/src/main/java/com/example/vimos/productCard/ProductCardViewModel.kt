package com.example.vimos.productCard

import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository

internal class ProductCardViewModel(
    private val repository: MainRepository
) : BaseViewModel<ProductCardUiState> () {
    override fun createInitialState() = ProductCardUiState()
}
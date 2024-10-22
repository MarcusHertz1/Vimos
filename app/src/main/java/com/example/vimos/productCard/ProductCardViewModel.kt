package com.example.vimos.productCard

import com.example.vimos.appbase.BaseViewModel

internal class ProductCardViewModel : BaseViewModel<ProductCardUiState> () {
    override fun createInitialState() = ProductCardUiState()
}
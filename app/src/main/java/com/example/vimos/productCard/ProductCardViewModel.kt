package com.example.vimos.productCard

import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ProductCardViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel<ProductCardUiState> () {
    override fun createInitialState() = ProductCardUiState()
}
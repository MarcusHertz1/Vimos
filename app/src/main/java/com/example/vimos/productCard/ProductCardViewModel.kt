package com.example.vimos.productCard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository
import com.example.vimos.appbase.SLUG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProductCardViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductCardUiState>() {
    override fun createInitialState() = ProductCardUiState()

    private val slug by lazy {
        savedStateHandle.get<String>(SLUG).orEmpty()
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getProductData(slug).body()?.let { dataFromServer ->
                Product(
                    iconUrl = dataFromServer.images?.firstOrNull()?.original.orEmpty(),
                    discount = dataFromServer.purchase?.discount?.toString().orEmpty(),
                    sku = dataFromServer.sku?.toString().orEmpty(),
                    title = dataFromServer.title.orEmpty(),
                    price = dataFromServer.purchase?.price?.toString().orEmpty(),
                    oldPrice = dataFromServer.purchase?.priceOld?.toString().orEmpty(),
                    units = dataFromServer.units.orEmpty(),
                    slug = slug
                ).let { product ->
                    setState {
                        copy(
                            data = product
                        )
                    }
                }
            }
        }
    }
}
package com.example.vimos.productCatalog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository
import com.example.vimos.appbase.SLUG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProductCatalogViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductCatalogUiState>() {
    override fun createInitialState() = ProductCatalogUiState()

    private val slug by lazy {
        savedStateHandle.get<String>(SLUG).orEmpty()
    }

    init {
        loadSlugsData()
    }

    private fun loadSlugsData() {
        viewModelScope.launch {
            val data = repository.getSlugsData(slug)
            val dataFromServer = data.body()
            setState {
                copy(
                    slugList = dataFromServer ?: emptyList()
                )
            }
            loadDataFromServer()
        }
    }

    private fun loadDataFromServer() {
        viewModelScope.launch {
            state.value.slugList.forEach {
                it.slug?.let { slug ->
                    async {
                        repository.getProductData(slug).body()?.let { dataFromServer ->
                            Product(
                                iconUrl = dataFromServer.images?.firstOrNull()?.original.orEmpty(),
                                discount = dataFromServer.purchase?.discount?.toString().orEmpty(),
                                sku = dataFromServer.sku?.toString().orEmpty(),
                                title = dataFromServer.title.orEmpty(),
                                price = dataFromServer.purchase?.price?.toString().orEmpty(),
                                oldPrice = dataFromServer.purchase?.priceOld?.toString().orEmpty(),
                                units = dataFromServer.units.orEmpty()
                            ).let { product ->
                                setState {
                                    copy(
                                        data = data + product
                                    )
                                }
                            }
                        }
                    }.await()
                }
            }
        }
    }

}
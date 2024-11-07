package com.example.vimos.productCatalog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository
import com.example.vimos.appbase.SLUG
import com.example.vimos.appbase.TOP_BAR_TITLE
import com.example.vimos.catalog.api.SlugElement
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

    private val topBarTitle by lazy {
        savedStateHandle.get<String>(TOP_BAR_TITLE).orEmpty()
    }

    init {
        val topBar = topBarTitle
        setState {
            copy(
                topBarTitle = topBar
            )
        }
        loadSlugsData()
    }

    private fun loadSlugsData() {
        viewModelScope.launch {
            var dataFromServer = emptyList<SlugElement>()
            async {
                dataFromServer = repository.getSlugsData(slug).body() ?: emptyList()
            }.await()
            dataFromServer.forEach {
                it.slug?.let { slug ->
                    async {
                        repository.getProductData(slug).body()?.let { dataFromServer ->
                            val price = (dataFromServer.purchase?.price ?: 0) / 100
                            val oldPrice = (dataFromServer.purchase?.priceOld ?: 0) / 100
                            Product(
                                iconUrl = dataFromServer.images?.firstOrNull()?.original.orEmpty(),
                                discount = dataFromServer.purchase?.discount?.toString().orEmpty(),
                                sku = dataFromServer.sku?.toString().orEmpty(),
                                title = dataFromServer.title.orEmpty(),
                                price = if (price == 0) "" else price.toString(),
                                oldPrice = if (oldPrice == 0) "" else oldPrice.toString(),
                                units = dataFromServer.units.orEmpty(),
                                slug = slug
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
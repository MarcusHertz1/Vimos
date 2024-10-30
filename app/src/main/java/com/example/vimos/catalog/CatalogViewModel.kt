package com.example.vimos.catalog

import androidx.lifecycle.viewModelScope
import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel  @Inject constructor(private val repository: MainRepository): BaseViewModel<CatalogUiState>() {
    override fun createInitialState() = CatalogUiState()

    init {
        loadCatalogData()
    }

    private fun loadCatalogData() {
        viewModelScope.launch {
            val data = repository.getCatalog()
            println("data=$data")
            val dataFromServer = data.body()
            setState {
                copy(
                    dataFromServer = dataFromServer ?: emptyList()
                )
            }
            setShowingDate()
        }
    }

    private fun setShowingDate() {
        var categoryData = state.value.dataFromServer
        state.value.depthIndexList.forEach {
            categoryData = categoryData[it].subCategories ?: emptyList()
        }
        val showingData = categoryData.map {
            CatalogItem(
                id = it.id,
                iconUrl = it.icon.orEmpty(),
                title = it.title.orEmpty(),

                )
        }
        setState {
            copy(
                showingData = showingData
            )
        }
    }


}
package com.example.vimos.catalog

import androidx.lifecycle.viewModelScope
import com.example.vimos.appbase.BaseViewModel
import com.example.vimos.appbase.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel  @Inject constructor(
    private val repository: MainRepository):
    BaseViewModel<CatalogUiState>() {
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
            updateShowingDate()
        }
    }

    internal fun addDepth(depth: Int){
        val result = state.value.depthIndexList.toMutableList()
        result.add(depth)
        setState {
            copy(
                depthIndexList = result
            )
        }
        updateShowingDate()
    }

    internal fun removeDepth(){
        val result = state.value.depthIndexList.toMutableList()
        result.removeLastOrNull()
        setState {
            copy(
                depthIndexList = result
            )
        }
        updateShowingDate()
    }

    private fun updateShowingDate() {
        var categoryData = state.value.dataFromServer
        var topBarTitle = ""
        state.value.depthIndexList.forEach { item ->
            topBarTitle = categoryData[item].title.orEmpty()
            categoryData = categoryData[item].subCategories ?: emptyList()
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
                showingData = showingData,
                topBarTitle = topBarTitle
            )
        }
    }

}
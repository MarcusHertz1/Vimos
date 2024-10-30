package com.example.vimos.appbase

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<STATE: UiState> : ViewModel() {

    private val initialState: STATE by lazy { createInitialState() }
    protected abstract fun createInitialState(): STATE

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    protected fun setState(newState: STATE.() -> STATE) {
        _state.update {
            newState.invoke(it)
        }
    }

}
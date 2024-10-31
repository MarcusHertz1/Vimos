package com.example.vimos.appbase

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
fun Bundle?.toViewModelArguments(): MutableCreationExtras =
    MutableCreationExtras(
        LocalViewModelStoreOwner.current.let {
            when(it){
                is HasDefaultViewModelProviderFactory -> it.defaultViewModelCreationExtras
                else -> CreationExtras.Empty
            }
        }
    ).apply { this@toViewModelArguments?.let { set(DEFAULT_ARGS_KEY, it) } }
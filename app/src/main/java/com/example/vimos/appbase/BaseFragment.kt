package com.example.vimos.appbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

abstract class BaseFragment : Fragment() {
    @Stable
    private val resultChannel = Channel<Bundle>(onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Create(arguments, resultChannel)
            }
        }
    }

    @Composable
    abstract fun Create(arguments: Bundle?, resultChannel: Channel<Bundle>)
}
package com.example.vimos.productCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vimos.ui.theme.VimosTheme

class ProductCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                ProductCardScreen()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCardScreen(
    viewModel: ProductCardViewModel = viewModel(), preview: Boolean = false
) {
    val state by viewModel.state.collectAsState()
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    "",
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(16.dp)
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(16.dp)
                )
            }
        )

    }
    ) { padding ->
        ProductCardList(modifier = Modifier.padding(padding))
    }
}

@Composable
private fun ProductCardList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            imageVector = Icons.Filled.Share,
            contentDescription = "Localized description"
        )
        Text(
            color = Color.White,
            text = "-15%",
            fontSize = 26.sp,
            modifier = Modifier
                .background(Color.Red, RoundedCornerShape(5.dp))
                .padding(6.dp)
        )
        Text(
            color = Color.Gray,
            text = "Арт. 24764168"
        )
        Text(text = "Product name", fontSize = 24.sp)

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                color = Color.Red,
                text = "2 200 ₽/шт.",
                fontSize = 30.sp
            )
            Text(
                text = "2 400 ₽/шт.",
                fontSize = 22.sp
            )
        }
    }

}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun CatalogScreenPreview() {
    VimosTheme {
        ProductCardScreen(preview = true)
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProductCardListPreview() {
    VimosTheme {
        ProductCardList()
    }
}
package com.example.vimos.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vimos.R
import com.example.vimos.ui.theme.VimosTheme
import com.skydoves.landscapist.glide.GlideImage


class CatalogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                CatalogScreen()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogScreen(
    viewModel: CatalogViewModel = viewModel(), preview: Boolean = false
) {
    val state by viewModel.state.collectAsState()
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = { Icons.AutoMirrored.Outlined.ArrowBack },
            title = {
                Text(if (state.depth == 0) stringResource(R.string.start_top_bar) else state.topBarTitle)
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(R.color.Vimos),
            )
        )
    }) { padding ->
        CatalogList(
            modifier = Modifier.padding(padding), if (preview) previewCatalogList else state.data
        )
    }
}

@Composable
private fun CatalogList(
    modifier: Modifier = Modifier, data: List<CatalogItem>
) {
    LazyColumn(modifier = modifier) {
        items(data) {
            CatalogListElement(it.iconUrl, it.title)
        }
    }
}

@Composable
private fun CatalogListElement(iconUrl: String? = null, title: String) {
    val iconSize = 42.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = iconSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!iconUrl.isNullOrBlank()) {
            GlideImage(imageModel = { iconUrl }, success = { image, _ ->
                Icon(
                    bitmap = image.imageBitmap!!,
                    contentDescription = title,
                    modifier = Modifier
                        .height(iconSize)
                        .width(iconSize)
                )
            })
        }
        Text(
            text = title,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = if (!iconUrl.isNullOrBlank()) 22.dp else 22.dp + iconSize)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

private val previewCatalogList = listOf(
    CatalogItem(id = 0, iconUrl = "", title = "Стройматериалы"),
    CatalogItem(id = 1, iconUrl = "", title = "Пиломатериалы"),
)

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun CatalogScreenPreview() {
    VimosTheme {
        CatalogScreen(preview = true)
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun CatalogListPreview() {
    VimosTheme {
        CatalogList(data = previewCatalogList)
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun CatalogListElementPreview() {
    VimosTheme {
        CatalogListElement(iconUrl = "", "Пиломатериалы")
    }
}
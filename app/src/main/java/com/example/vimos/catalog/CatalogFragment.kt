package com.example.vimos.catalog

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import com.example.vimos.MainActivity
import com.example.vimos.R
import com.example.vimos.appbase.BaseFragment
import com.example.vimos.appbase.NavigationCommand
import com.example.vimos.appbase.SLUG
import com.example.vimos.ui.theme.VIMOS_TOOLBAR
import com.example.vimos.ui.theme.VimosTheme
import kotlinx.coroutines.channels.Channel

class CatalogFragment : BaseFragment() {
    @Composable
    override fun Create(arguments: Bundle?, resultChannel: Channel<Bundle>) {
        val navController = findNavController() // Получаем NavController из фрагмента
        CatalogScreen(navController, (activity as? MainActivity))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogScreen(
    navController: NavController,
    activity: MainActivity?,
    viewModel: CatalogViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.navigationCommands.collect { command ->
            when (command) {
                is NavigationCommand.GoToProductCatalog -> {
                    navController.navigate(R.id.action_catalogFragment_to_productCatalogFragment, Bundle().apply {
                        putString(SLUG,command.slug)
                    })
                }
            }
        }
    }
    BackHandler {
        if (state.depthIndexList.isNotEmpty()){
            viewModel.removeDepth()
        } else activity?.finish()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (state.depthIndexList.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Вернуться назад",
                            modifier = Modifier
                                .clickable {
                                    viewModel.removeDepth()
                                }
                                .size(48.dp)
                                .padding(12.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text =
                        if (state.depthIndexList.isEmpty()) stringResource(R.string.start_top_bar)
                        else state.topBarTitle,
                        modifier =  Modifier.padding(start = if (state.depthIndexList.isNotEmpty()) 6.dp else 0.dp)
                    )

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VIMOS_TOOLBAR,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }) { padding ->
        CatalogList(
            modifier = Modifier.padding(padding),
            state.showingData,
            onItemClick = { itemIndex ->
                viewModel.addDepth(itemIndex)
            }
        )
    }
}

@Composable
private fun CatalogList(
    modifier: Modifier = Modifier,
    data: List<CatalogItem>,
    onItemClick: (Int) -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        itemsIndexed(data) { index, item ->
            CatalogListElement(item.iconUrl, item.title) { onItemClick(index) }
        }
    }
}

@Composable
private fun CatalogListElement(
    iconUrl: String? = null,
    title: String,
    onItemClick: () -> Unit = {}
) {
    val iconSize = 24.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = iconSize)
            .padding(vertical = 12.dp)
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://vsevolojsk.vimos.ru$iconUrl",
            contentDescription = title,
            modifier = Modifier
                .height(iconSize)
                .width(iconSize),
        )
        Text(
            text = title,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 16.dp)
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
private fun CatalogListPreview() {
    VimosTheme {
        CatalogList(data = previewCatalogList)
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun CatalogListElementPreview() {
    VimosTheme {
        CatalogListElement(iconUrl = "", "Сантехника и инженеры системы")
    }
}
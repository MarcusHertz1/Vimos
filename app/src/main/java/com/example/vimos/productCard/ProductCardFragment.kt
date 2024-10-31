package com.example.vimos.productCard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import com.example.vimos.R
import com.example.vimos.appbase.BaseFragment
import com.example.vimos.appbase.toViewModelArguments
import com.example.vimos.ui.theme.VimosTheme
import kotlinx.coroutines.channels.Channel

class ProductCardFragment : BaseFragment() {
    @Composable
    override fun Create(arguments: Bundle?, resultChannel: Channel<Bundle>) {
        val navController = findNavController()
        ProductCardState(arguments, navController)
    }

}

@Composable
private fun ProductCardState(
    arguments: Bundle?,
    navController: NavController,
    viewModel: ProductCardViewModel = viewModel(extras = arguments.toViewModelArguments())
) {
    val state by viewModel.state.collectAsState()
    ProductCardScreen(state) { navController.popBackStack() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCardScreen(
    state: ProductCardUiState,
    goBack: () -> Unit = {},
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            goBack()
                        },
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            shareText(context, "Арт. ${state.data.sku}\n${state.data.title}")
                        }
                )
            },
        )
    }
    ) { padding ->
        ProductCardList(
            modifier = Modifier.padding(padding),
            state = state
        )
    }
}

@Composable
private fun ProductCardList(modifier: Modifier = Modifier, state: ProductCardUiState) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AsyncImage(
            model = "https://storage.vimos.ru/filter/1200w_webp${state.data.iconUrl}",
            contentDescription = state.data.title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp)
                .padding(16.dp),
        )
        if (state.data.discount.isNotBlank()) {
            Text(
                color = Color.White,
                text = "-${state.data.discount}%",
                fontSize = 20.sp,
                modifier = Modifier
                    .background(Color.Red, RoundedCornerShape(5.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
        Text(
            color = Color.Gray,
            text = stringResource(R.string.sku, state.data.sku)
        )
        Text(text = state.data.title, fontSize = 24.sp)

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                color = if (state.data.oldPrice.isNotBlank()) Color.Red else Color.Unspecified,
                text = stringResource(R.string.price, state.data.price, state.data.units),
                fontSize = 30.sp
            )
            if (state.data.oldPrice.isNotBlank()) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Text(
                        text = stringResource(
                            R.string.price,
                            state.data.oldPrice,
                            state.data.units
                        ),
                        fontSize = 22.sp,
                    )
                    MeasureUnconstrainedViewWidth(
                        viewToMeasure = {
                            Text(
                                text = stringResource(
                                    R.string.price,
                                    state.data.oldPrice,
                                    state.data.units
                                ),
                                fontSize = 22.sp,
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                        }
                    ) { measuredWidth ->
                        Image(
                            painter = painterResource(R.drawable.ic_line_2),
                            contentDescription = "Localized description",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.width(measuredWidth)
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun MeasureUnconstrainedViewWidth(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (measuredWidth: Dp) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val measuredWidth = subcompose("viewToMeasure", viewToMeasure)[0]
            .measure(Constraints()).width.toDp()
        val contentPlaceable = subcompose("content") {
            content(measuredWidth)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}

fun shareText(context: Context, textToShare: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textToShare)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Поделиться через"))
}

val previewState = ProductCardUiState(
    data = Product(
        iconUrl = "",
        discount = "15",
        sku = "24764168",
        title = "Грунтовка Eskaro Aquastop Contact адгез. для невпит./поверх. 1,5 кг",
        price = "2 200",
        oldPrice = "2 500",
        units = "шт"
    )
)

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun CatalogScreenPreview() {
    VimosTheme {
        ProductCardScreen(previewState)
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProductCardListPreview() {
    VimosTheme {
        ProductCardList(state = previewState)
    }
}
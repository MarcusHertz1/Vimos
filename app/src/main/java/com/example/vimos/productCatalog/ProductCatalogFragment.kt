package com.example.vimos.productCatalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vimos.R
import com.example.vimos.ui.theme.VimosTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

class ProductCatalogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                ProductCatalogState()

            }
        }
    }
}

@Composable
private fun ProductCatalogState(
    viewModel: ProductCatalogViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    ProductCatalogScreen(state)
}

@Composable
private fun ProductCatalogScreen(
    state: ProductCatalogUiState
) {
    Scaffold { padding ->
        ProductCatalogList(
            modifier = Modifier.padding(padding),
            state = state
        )
    }
}

@Composable
private fun ProductCatalogList(modifier: Modifier = Modifier, state: ProductCatalogUiState) {
    LazyColumn(modifier = modifier) {
        items(state.data) {
            ProductListElement(it)
        }
    }
}

@Composable
private fun ProductListElement(element: ProductCatalogUiState.Product) {
    Column {
        Row(modifier = Modifier.padding(20.dp)) {
            Box {
                Text(
                    color = Color.White,
                    text = "-${element.discount}%",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .background(Color.Red, RoundedCornerShape(5.dp))
                        .padding(vertical = 2.dp, horizontal = 6.dp)
                )
                val modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
                GlideImage(
                    imageModel = { element.iconUrl },
                    success = { image, _ ->
                        Icon(
                            bitmap = image.imageBitmap!!,
                            contentDescription = "Image",
                            modifier = modifier
                        )
                    },
                    failure = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Image",
                            modifier = modifier
                        )
                    },
                    imageOptions = ImageOptions(alignment = Alignment.Center)
                )
            }
            Column(modifier = Modifier.padding(start = 20.dp)) {
                Text(
                    color = Color.Gray,
                    text = stringResource(R.string.sku, element.sku)
                )
                Text(element.title)
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.price, element.price, element.units),
                        fontSize = 20.sp,
                    )
                    if (element.oldPrice.isNotBlank()) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Text(
                                text = stringResource(
                                    R.string.price,
                                    element.oldPrice,
                                    element.units
                                ),
                                fontSize = 18.sp,
                            )
                            MeasureUnconstrainedViewWidth(
                                viewToMeasure = {
                                    Text(
                                        text = stringResource(
                                            R.string.price,
                                            element.oldPrice,
                                            element.units
                                        ),
                                        fontSize = 18.sp,
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
        IconButton(
            onClick = {},
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Share",
                modifier = Modifier
                    .size(20.dp)
            )
        }
        HorizontalDivider()
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

val previewElement = ProductCatalogUiState.Product(
    iconUrl = "",
    discount = "15",
    sku = "24764168",
    title = "Грунтовка Eskaro Aquastop Contact адгез. для невпит./поверх. 1,5 кг",
    price = "2 200",
    oldPrice = "2 500",
    units = "шт"
)

val previewState = ProductCatalogUiState(
    data = List(3) {
        previewElement
    }
)

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun CatalogScreenPreview() {
    VimosTheme {
        ProductCatalogScreen(previewState)
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProductCardElementPreview() {
    VimosTheme {
        ProductListElement(element = previewElement)
    }
}
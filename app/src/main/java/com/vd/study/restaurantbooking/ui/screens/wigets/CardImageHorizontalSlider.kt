package com.vd.study.restaurantbooking.ui.screens.wigets

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.ui.model.offsetForPage
import kotlin.math.absoluteValue

@Composable
fun CardImageHorizontalSlider(
    modifier: Modifier = Modifier,
    state: PagerState,
    items: Array<String>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val configuration = LocalConfiguration.current
    HorizontalPager(
        pageCount = items.size,
        state = state,
        contentPadding = contentPadding,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) { page ->
        Card(
            colors = CardDefaults.cardColors(Color.Transparent),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = state.offsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1 - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            var isLoading by rememberSaveable { mutableStateOf(true) }

            this@Card.AnimatedVisibility(visible = isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }

            AsyncImage(
                model = items[page],
                contentDescription = null,
                modifier = Modifier
                    .height(
                        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 260.dp
                        else 220.dp
                    )
                    .width(
                        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 500.dp
                        else 300.dp
                    )
                    .offset {
                        val pageOffset = state.offsetForPage(page)
                        IntOffset(
                            x = (90.dp * pageOffset).roundToPx(),
                            y = 0,
                        )
                    },
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                onLoading = { isLoading = true },
                onError = { isLoading = false },
                onSuccess = { isLoading = false },
                error = painterResource(id = R.drawable.round_hide_image)
            )
        }
    }
}
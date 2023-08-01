package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vd.study.restaurantbooking.R

@Composable
fun BookingFloatingActionButton(
    modifier: Modifier = Modifier,
    price: Double,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        ExtendedFloatingActionButton(
            text = {
                Text(
                    text = if (price != 0.0) {
                        stringResource(id = R.string.buy_for_format, price.toString())
                    } else {
                        stringResource(id = R.string.buy)
                    }
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.round_shopping_cart),
                    contentDescription = null
                )
            },
            onClick = onClick,
            modifier = Modifier
                .padding(end = 20.dp, bottom = 20.dp)
                .align(Alignment.BottomEnd)
        )
    }
}
package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconWithText(
    modifier: Modifier = Modifier,
    @DrawableRes drawableId: Int,
    @StringRes stringId: Int
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = stringResource(id = stringId),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}
package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DropDownListItem(
    modifier: Modifier = Modifier,
    @StringRes stringId: Int,
    @DrawableRes drawableId: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = drawableId), contentDescription = null)
        Text(
            text = stringResource(id = stringId),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
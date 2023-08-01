package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vd.study.restaurantbooking.R

@Composable
fun SimpleNotificationScreen(
    modifier: Modifier = Modifier,
    @DrawableRes imageId: Int,
    @StringRes text: Int,
    isReloadButtonVisible: Boolean = false,
    onReloadButtonClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = stringResource(id = text),
            modifier = Modifier
                .size(172.dp)
                .padding(top = 16.dp)
        )

        Text(
            text = stringResource(id = text),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            textAlign = TextAlign.Center
        )

        AnimatedVisibility(visible = isReloadButtonVisible) {
            IconButton(
                onClick = { onReloadButtonClick?.let { it() } }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_replay),
                    contentDescription = stringResource(id = R.string.start_reload_description)
                )
            }
        }
    }
}
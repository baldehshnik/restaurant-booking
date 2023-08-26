package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlertDialogTitle(
    modifier: Modifier = Modifier,
    titleTextId: Int,
    titleIconId: Int
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = titleIconId),
                contentDescription = null,
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(
                text = stringResource(id = titleTextId),
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
        }

        Divider(modifier = Modifier.padding(top = 6.dp))
    }
}
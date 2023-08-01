package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogCircularProgress(
    modifier: Modifier = Modifier,
    properties: DialogProperties
) {
    Dialog(
        onDismissRequest = {},
        properties = properties
    ) {
        Box(modifier = modifier) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
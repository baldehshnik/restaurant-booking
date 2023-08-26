package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.vd.study.restaurantbooking.R

@Composable
fun BookedNotificationDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            AlertDialogOkayConfirmButton(onDismiss = onDismiss)
        },
        title = {
            AlertDialogTitle(titleTextId = R.string.success, titleIconId = R.drawable.round_new_releases)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = stringResource(id = R.string.booked_notification),
                    fontSize = 16.sp
                )
            }
        }
    )
}
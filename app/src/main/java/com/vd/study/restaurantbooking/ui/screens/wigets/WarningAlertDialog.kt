package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vd.study.restaurantbooking.R

@Composable
fun WarningAlertDialog(
    modifier: Modifier = Modifier,
    @StringRes contentId: Int,
    onDismiss: () -> Unit
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            AlertDialogOkayConfirmButton(onDismiss = onDismiss)
        },
        title = {
            AlertDialogTitle(
                titleTextId = R.string.warning,
                titleIconId = R.drawable.round_error
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(text = stringResource(id = contentId), fontSize = 16.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.visit_settings_to_view_more_information),
                    fontSize = 15.sp
                )
            }
        }
    )
}
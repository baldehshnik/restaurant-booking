package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vd.study.restaurantbooking.R

@Composable
fun AlertDialogOkayConfirmButton(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onDismiss
    ) {
        Text(text = stringResource(id = R.string.okay))
    }
}
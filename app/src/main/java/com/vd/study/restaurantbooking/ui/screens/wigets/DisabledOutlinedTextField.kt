package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun DisabledOutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    @StringRes labelId: Int,
    @DrawableRes painterId: Int,
    @StringRes contentDescription: Int
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = modifier,
        label = { Text(text = stringResource(id = labelId)) },
        enabled = false,
        trailingIcon = {
            Icon(
                painter = painterResource(id = painterId),
                contentDescription = stringResource(id = contentDescription)
            )
        },
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.colors(
            disabledLabelColor = if (value.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant
            else MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
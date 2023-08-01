package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vd.study.restaurantbooking.R

@Composable
fun BottomSheetTimePicker(
    modifier: Modifier = Modifier,
    onConfirm: (hour: Int, minute: Int, is24Hour: Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberTimePickerState()
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
            ) {
                TimePicker(
                    state = state,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 8.dp, end = 20.dp)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.close))
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    TextButton(
                        onClick = { onConfirm(state.hour, state.minute, state.is24hour) }
                    ) {
                        Text(text = stringResource(id = R.string.okay))
                    }
                }
            }
        }
    }
}
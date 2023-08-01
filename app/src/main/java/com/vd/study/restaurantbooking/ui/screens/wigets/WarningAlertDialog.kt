package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vd.study.restaurantbooking.R

@Composable
fun WarningAlertDialog(
    modifier: Modifier = Modifier,
    @StringRes contentId: Int,
    onDismiss: () -> Unit,
    showSettingsTooltip: Boolean
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.okay))
            }
        },
        title = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_error),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 10.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.warning),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Divider(modifier = Modifier.padding(top = 6.dp))
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(text = stringResource(id = contentId), fontSize = 16.sp)
                if (showSettingsTooltip) {
                    var isInfoVisible by remember { mutableStateOf(false) }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { isInfoVisible = !isInfoVisible }
                    ) {
                        Text(
                            text = stringResource(id = R.string.show_more_information),
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic
                        )

                        Icon(
                            painter = painterResource(
                                id = if (!isInfoVisible) R.drawable.round_keyboard_arrow_down
                                else R.drawable.round_keyboard_arrow_up
                            ),
                            contentDescription = null
                        )
                    }

                    if (isInfoVisible) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, bottom = 4.dp),
                            text = stringResource(id = R.string.periods_information),
                            fontSize = 15.sp
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.visit_settings_to_view_more_information),
                        fontSize = 15.sp
                    )
                }
            }
        }
    )
}
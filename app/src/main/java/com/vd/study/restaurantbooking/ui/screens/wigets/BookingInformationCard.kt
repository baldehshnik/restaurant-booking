package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vd.study.restaurantbooking.R

@Composable
fun BookingInformationCard(
    modifier: Modifier = Modifier,
    tables: List<Int>,
    isTablesVisible: Boolean,
    isTablesLoaded: Boolean,
    @StringRes titleId: Int,
    time: String,
    date: String,
    onItemClick: (item: Int) -> Unit,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = stringResource(id = titleId),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            DisabledOutlinedTextField(
                value = date,
                labelId = R.string.date,
                painterId = R.drawable.round_calendar_month,
                contentDescription = R.string.date_select_description,
                modifier = Modifier
                    .padding(start = 16.dp, end = 5.dp)
                    .weight(1f)
                    .clickable(onClick = onDateClick)
            )

            DisabledOutlinedTextField(
                value = time,
                labelId = R.string.time,
                painterId = R.drawable.round_access_time,
                contentDescription = R.string.time_select_description,
                modifier = Modifier
                    .padding(start = 5.dp, end = 16.dp)
                    .weight(1f)
                    .clickable(onClick = onTimeClick)
            )
        }

        AnimatedVisibility(visible = isTablesVisible) {
            Column {
                Text(
                    text = stringResource(id = R.string.available_tables),
                    modifier = Modifier.padding(top = 10.dp, start = 16.dp, bottom = 16.dp),
                    fontSize = 16.sp
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    if (isTablesLoaded) {
                        if (tables.isEmpty()) {
                            IconWithText(
                                drawableId = R.drawable.round_no_meals,
                                stringId = R.string.available_nothing
                            )
                        } else {
                            TableNumberList(
                                items = tables,
                                onItemClick = onItemClick
                            )
                        }
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
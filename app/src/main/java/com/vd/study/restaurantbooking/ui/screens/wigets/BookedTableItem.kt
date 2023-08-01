package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.domain.model.LocalTableModel
import com.vd.study.restaurantbooking.utils.MonthConverter

@Composable
fun BookedTableItem(
    item: LocalTableModel,
    containerColor: Color,
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onItemClick: (() -> Unit)? = null
) {
    var isItemPressed by remember { mutableStateOf(false) }
    var menuIsVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        onClick = {
            isItemPressed = !isItemPressed
            onItemClick?.let { it() }
        },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        id = R.string.booked_table_head_format,
                        item.tableNumber.toString(),
                        item.price.toString()
                    ),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    maxLines = if (isItemPressed) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(onClick = { menuIsVisible = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_more_vert),
                        contentDescription = stringResource(
                            id = R.string.booked_table_item_abilities_menu_description
                        )
                    )
                }

                DropdownMenu(
                    expanded = menuIsVisible,
                    onDismissRequest = { menuIsVisible = false },
                    offset = DpOffset(x = 220.dp, y = (-6).dp)
                ) {
                    if (!item.completed) {
                        DropdownMenuItem(
                            onClick = onCancelClick,
                            text = {
                                DropDownListItem(
                                    stringId = R.string.cancel,
                                    drawableId = R.drawable.round_close
                                )
                            }
                        )
                    }

                    DropdownMenuItem(
                        onClick = onDeleteClick,
                        text = {
                            DropDownListItem(
                                stringId = R.string.delete,
                                drawableId = R.drawable.round_delete_sweep
                            )
                        }
                    )
                }
            }

            Text(
                text = stringResource(
                    id = R.string.booked_by_format,
                    item.firstname,
                    item.lastname,
                    item.patronymic
                ),
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                maxLines = if (isItemPressed) Int.MAX_VALUE else 1,
                fontWeight = FontWeight(450),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(
                    id = R.string.date_and_time_format,
                    item.time,
                    MonthConverter().getFullDateAndTimeDisplayData(item.date)
                ),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }
    }
}
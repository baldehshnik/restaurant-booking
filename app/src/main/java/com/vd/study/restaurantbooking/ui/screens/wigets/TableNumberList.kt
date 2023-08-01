package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vd.study.restaurantbooking.ui.screens.wigets.TableNumberItem

@Composable
fun TableNumberList(
    modifier: Modifier = Modifier,
    items: List<Int>,
    onItemClick: (item: Int) -> Unit
) {
    var selectedItem by rememberSaveable { mutableStateOf(items[0]) }
    LaunchedEffect(items.size) {
        selectedItem = items[0]
        onItemClick(selectedItem)
    }

    LazyRow(modifier = modifier) {
        items(items) { item ->
            val selected = item == selectedItem
            TableNumberItem(
                cutCornerShapeSize = 20.dp,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(96.dp)
                    .selectable(
                        selected = selected,
                        onClick = {}
                    ),
                text = item.toString(),
                onClick = {
                    selectedItem = item
                    onItemClick(item)
                },
                containerColor = if (selected) MaterialTheme.colorScheme.tertiaryContainer
                else MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}
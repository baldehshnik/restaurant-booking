package com.vd.study.restaurantbooking.ui.screens.wigets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vd.study.restaurantbooking.domain.model.LocalTableModel

@Composable
fun BookedTableVerticalList(
    items: List<LocalTableModel>,
    modifier: Modifier = Modifier,
    onCancelClick: (item: LocalTableModel) -> Unit,
    onDeleteClick: (item: LocalTableModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        state = rememberLazyListState(),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp)
    ) {
        items(items, key = { item -> item.id }) { item ->
            val state = remember {
                MutableTransitionState(false).apply {
                    targetState = true
                }
            }

            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BookedTableItem(
                    modifier = Modifier.padding(vertical = 6.dp),
                    item = item,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    onCancelClick = { onCancelClick(item) },
                    onDeleteClick = { onDeleteClick(item) }
                )
            }
        }
    }
}
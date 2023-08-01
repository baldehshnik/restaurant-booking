package com.vd.study.restaurantbooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.domain.model.LocalTableModel
import com.vd.study.restaurantbooking.ui.model.ErrorVisibilityWithMessage
import com.vd.study.restaurantbooking.ui.model.LocalBookingTablesViewState
import com.vd.study.restaurantbooking.ui.screens.wigets.BookedTableVerticalList
import com.vd.study.restaurantbooking.ui.screens.wigets.SimpleNotificationScreen
import com.vd.study.restaurantbooking.ui.viewmodel.HubViewModel

@Composable
fun HubScreen(
    viewModel: HubViewModel,
    snackbarHostState: SnackbarHostState,
) {
    val readResult by viewModel.readResponse.observeAsState(LocalBookingTablesViewState.LocalBookingTablesLoadingState)

    val state by viewModel.state.observeAsState(HubViewModel.State())
    val errorMessage = stringResource(id = R.string.error)

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefresh)

    if (state.isSnackbarVisible.isVisible) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(errorMessage)
            viewModel.changeState(isErrorSnackbarVisible = ErrorVisibilityWithMessage(isVisible = false))
        }
    }

    if (state.isWarningVisible) {
        val onDismiss = {
            viewModel.pressedItem = null
            viewModel.changeState(isWarningVisible = false)
        }
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    viewModel.cancel()
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            title = {
                Text(
                    text = stringResource(id = R.string.warning),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(id = R.string.close))
                }
            },
            text = {
                Text(
                    text = stringResource(id = R.string.warning),
                    fontSize = 16.sp
                )
            }
        )
    }

    HubScreenPresent(
        modifier = Modifier.fillMaxSize(),
        onRefresh = viewModel::read,
        refreshState = swipeRefreshState,
        items = viewModel.items,
        result = readResult,
        onItemMenuCancelClick = {
            viewModel.pressedItem = it
            viewModel.changeState(isWarningVisible = true)
        },
        onItemMenuDeleteCLick = { viewModel.delete(it) },
        onReloadButtonClick = viewModel::read
    )
}

@Composable
fun HubScreenPresent(
    modifier: Modifier = Modifier,
    refreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
    items: List<LocalTableModel>,
    result: LocalBookingTablesViewState,
    onItemMenuCancelClick: (item: LocalTableModel) -> Unit,
    onItemMenuDeleteCLick: (item: LocalTableModel) -> Unit,
    onReloadButtonClick: () -> Unit,
) {
    SwipeRefresh(state = refreshState, onRefresh = onRefresh) {
        Box(
            modifier = modifier
        ) {
            when (result) {
                is LocalBookingTablesViewState.LocalBookingTablesLoadingState -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.records_loading),
                            fontSize = 20.sp
                        )
                        CircularProgressIndicator()
                    }
                }

                is LocalBookingTablesViewState.LocalBookingTablesEmptyState -> {
                    SimpleNotificationScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        imageId = R.drawable.no_records,
                        text = R.string.empty_data
                    )
                }

                is LocalBookingTablesViewState.LocalBookingTablesLoadedState -> {
                    BookedTableVerticalList(
                        items = items,
                        onCancelClick = onItemMenuCancelClick,
                        onDeleteClick = onItemMenuDeleteCLick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                else -> {
                    SimpleNotificationScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        imageId = R.drawable.error,
                        text = R.string.error_notification,
                        isReloadButtonVisible = true,
                        onReloadButtonClick = onReloadButtonClick
                    )
                }
            }
        }
    }
}
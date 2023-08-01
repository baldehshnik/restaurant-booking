package com.vd.study.restaurantbooking.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.domain.model.LocalTableModel
import com.vd.study.restaurantbooking.domain.usecase.DeleteBookedTableFromLocalDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.DeleteBookedTableFromRemoteDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.ReadAllBookedTablesFromLocalDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.UpdateBookedTableInLocalDatabaseUseCase
import com.vd.study.restaurantbooking.ui.model.ErrorVisibilityWithMessage
import com.vd.study.restaurantbooking.ui.model.LocalBookingTablesViewState
import com.vd.study.restaurantbooking.utils.Dispatchers
import com.vd.study.restaurantbooking.utils.TimeFormat
import com.vd.study.restaurantbooking.utils.sealed.ReadingTableResponse
import com.vd.study.restaurantbooking.utils.sealed.Response
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HubViewModel @Inject constructor(
    private val dispatchers: Dispatchers,
    private val deleteBookedTableFromLocalDatabaseUseCase: DeleteBookedTableFromLocalDatabaseUseCase,
    private val readAllBookedTablesFromLocalDatabaseUseCase: ReadAllBookedTablesFromLocalDatabaseUseCase,
    private val updateBookedTableInLocalDatabaseUseCase: UpdateBookedTableInLocalDatabaseUseCase,
    private val deleteBookedTableFromRemoteDatabaseUseCase: DeleteBookedTableFromRemoteDatabaseUseCase
) : BaseViewModel() {

    val items = mutableStateListOf<LocalTableModel>()

    var pressedItem: LocalTableModel? = null

    private val _state = MutableLiveData(State())
    val state: LiveData<State> get() = _state

    private val _readResponse = MutableLiveData<LocalBookingTablesViewState>(
        LocalBookingTablesViewState.LocalBookingTablesLoadingState
    )
    val readResponse: LiveData<LocalBookingTablesViewState> get() = _readResponse

    fun delete(model: LocalTableModel, showErrorAfterCancellationOfBooking: Boolean = false) {
        changeState(isItemMenuExecution = true)
        viewModelScope.launch {
            when (deleteBookedTableFromLocalDatabaseUseCase(model)) {
                is Response.Correct -> items.remove(model)
                Response.Error -> {
                    if (!showErrorAfterCancellationOfBooking) {
                        changeState(isErrorSnackbarVisible = ErrorVisibilityWithMessage(isVisible = true))
                    } else {
                        changeState(
                            isErrorSnackbarVisible = ErrorVisibilityWithMessage(
                                isVisible = true,
                                messageId = R.string.failed_to_delete_canceled_entry
                            )
                        )
                    }
                }

                Response.Progress -> changeState(isItemMenuExecution = true)
            }
        }
        changeState(isItemMenuExecution = false)
    }

    fun cancel() {
        changeState(isItemMenuExecution = true)
        viewModelScope.launch {
            val selectedItem = pressedItem ?: return@launch
            when (deleteBookedTableFromRemoteDatabaseUseCase(selectedItem.toRemoteTableModel())) {
                Response.Progress -> changeState(isItemMenuExecution = true)
                Response.Error -> changeState(
                    isErrorSnackbarVisible = ErrorVisibilityWithMessage(
                        isVisible = true,
                        messageId = R.string.failed_to_cancel_booking
                    )
                )

                is Response.Correct -> delete(
                    selectedItem,
                    showErrorAfterCancellationOfBooking = true
                )
            }
        }
        changeState(isItemMenuExecution = false)
    }

    fun read() {
        _readResponse.value = LocalBookingTablesViewState.LocalBookingTablesLoadingState
        viewModelScope.launch {
            _readResponse.value = when (
                val answer = readAllBookedTablesFromLocalDatabaseUseCase()
            ) {
                ReadingTableResponse.Empty -> {
                    items.clear()
                    LocalBookingTablesViewState.LocalBookingTablesEmptyState
                }

                ReadingTableResponse.Error -> {
                    items.clear()
                    LocalBookingTablesViewState.LocalBookingTablesErrorState
                }

                is ReadingTableResponse.Correct -> {
                    withContext(dispatchers.defaultDispatcher) {
                        if (items != changeCompletedTables(answer.items)) {
                            items.clear()
                            items += changeCompletedTables(answer.items)
                        }
                    }
                    LocalBookingTablesViewState.LocalBookingTablesLoadedState
                }
            }
        }
    }

    private fun changeCompletedTables(tables: List<LocalTableModel>): List<LocalTableModel> {
        return tables.map { model ->
            if (TimeFormat().isTableBookingEnded(model.time, model.date)) {
                val newModel = model.copy(completed = true)
                viewModelScope.launch(dispatchers.ioDispatcher) {
                    updateBookedTableInLocalDatabaseUseCase(newModel)
                }
                newModel
            } else {
                model
            }
        }
    }

    fun changeState(
        isErrorSnackbarVisible: ErrorVisibilityWithMessage? = null,
        isItemMenuExecution: Boolean? = null,
        isWarningVisible: Boolean? = null,
        isRefresh: Boolean? = null
    ) {
        val oldState = state.value!!
        _state.value = state.value!!.copy(
            isSnackbarVisible = isErrorSnackbarVisible ?: oldState.isSnackbarVisible,
            isItemMenuExecution = isItemMenuExecution ?: oldState.isItemMenuExecution,
            isWarningVisible = isWarningVisible ?: oldState.isWarningVisible,
            isRefresh = isRefresh ?: oldState.isRefresh
        )
    }

    init {
        read()
    }

    data class State(
        val isSnackbarVisible: ErrorVisibilityWithMessage = ErrorVisibilityWithMessage(false),
        val isItemMenuExecution: Boolean = false,
        val isWarningVisible: Boolean = false,
        val isRefresh: Boolean = false
    )
}
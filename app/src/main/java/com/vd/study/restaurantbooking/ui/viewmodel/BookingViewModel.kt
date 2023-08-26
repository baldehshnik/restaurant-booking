package com.vd.study.restaurantbooking.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.domain.model.LocalTableModel
import com.vd.study.restaurantbooking.domain.model.RemoteTableModel
import com.vd.study.restaurantbooking.domain.usecase.BookTableInRemoteDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.ReadEmptyTablesFromRemoteDatabaseUseCase
import com.vd.study.restaurantbooking.ui.model.ToastModel
import com.vd.study.restaurantbooking.ui.model.WarningAlertDialogModel
import com.vd.study.restaurantbooking.utils.DateFormat
import com.vd.study.restaurantbooking.utils.Dispatchers
import com.vd.study.restaurantbooking.utils.PriceCounter
import com.vd.study.restaurantbooking.utils.SideEffect
import com.vd.study.restaurantbooking.utils.TimeFormat
import com.vd.study.restaurantbooking.utils.sealed.Response
import com.vd.study.restaurantbooking.utils.sealed.TablesReadingResponse
import com.vd.study.restaurantbooking.utils.sealed.TimeFormatVariables
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingViewModel @Inject constructor(
    private val dispatchers: Dispatchers,
    private val bookTableInRemoteDatabaseUseCase: BookTableInRemoteDatabaseUseCase,
    private val readEmptyTablesFromRemoteDatabaseUseCase: ReadEmptyTablesFromRemoteDatabaseUseCase
) : BaseViewModel() {

    private var timeIn24HoursFormat = ""

    private val _isToastVisible = MutableLiveData(SideEffect(ToastModel()))
    val isToastVisible: LiveData<SideEffect<ToastModel>> get() = _isToastVisible

    private var _saveBookedTableLiveData = MutableLiveData(SideEffect(false))
    val saveBookedTableLiveData: LiveData<SideEffect<Boolean>> get() = _saveBookedTableLiveData

    fun setDateValue(timeMillis: Long?) {
        if (timeMillis == null) {
            toast(R.string.incorrect_date)
            return
        }

        val dateFormat = DateFormat()
        val value = dateFormat.convertTimeMillisToString(timeMillis)
        if (dateFormat.isDateFormatProgrammable(value)) {
            if (!dateFormat.isDateCorrect(value)) {
                changeNotificationState(
                    isAlertDialogVisible = WarningAlertDialogModel(
                        isVisible = true,
                        stringId = R.string.incorrect_date_alert_information
                    )
                )
                return
            } else if (value == bookingState.value!!.date) {
                return
            }

            changeBookingState(
                date = dateFormat.convertProgrammableToReadableFormat(value)
            )
            countAndSetPrice()
            readTablesIfBookingDataCorrect()
        }
    }

    fun setTimeValue(value: String, is24HourFormat: Boolean) {
        when (TimeFormat().isTimeFormat(value)) {
            TimeFormatVariables.Correct -> {
                if (value == timeIn24HoursFormat) {
                    return
                }

                timeIn24HoursFormat = value
                changeBookingState(
                    time = if (is24HourFormat) value else TimeFormat().getTimeOf12HourFormat(value)
                )
                countAndSetPrice()
                readTablesIfBookingDataCorrect()
            }

            TimeFormatVariables.Error -> {
                changeNotificationState(
                    isAlertDialogVisible = WarningAlertDialogModel(
                        isVisible = true,
                        stringId = R.string.restaurant_closed_alert_information
                    )
                )
            }

            TimeFormatVariables.LittleTime -> {
                changeNotificationState(
                    isAlertDialogVisible = WarningAlertDialogModel(
                        isVisible = true,
                        stringId = R.string.incorrect_time_alert_information
                    )
                )
            }
        }
    }

    fun bookTable() {
        if (isBookingDataCorrect() && isPersonalDataCorrect()) {
            val bookingStateValue = bookingState.value!!
            val personalStateValue = personalState.value!!
            bookTableInDatabase(
                RemoteTableModel(
                    bookingStateValue.tableNumber.toInt(),
                    bookingStateValue.date,
                    bookingStateValue.price,
                    timeIn24HoursFormat,
                    personalStateValue.firstname,
                    personalStateValue.lastname,
                    personalStateValue.patronymic
                )
            )
        }
    }

    private fun isPersonalDataCorrect(): Boolean {
        val firstname = personalState.value!!.firstname
        val lastname = personalState.value!!.lastname
        val patronymic = personalState.value!!.patronymic
        if (firstname.isEmpty()) {
            changePersonalState(isErrorFirstname = true)
            return false
        } else if (lastname.isEmpty()) {
            changePersonalState(isErrorLastname = true)
            return false
        } else if (patronymic.isEmpty()) {
            changePersonalState(isErrorPatronymic = true)
            return false
        }
        return true
    }

    private fun readTablesIfBookingDataCorrect() {
        if (isBookingDataCorrect(indicateLocationError = false)) {
            changeBookingState(isTablesVisible = true, isTablesLoaded = false)
            readEmptyTables()
        } else {
            changeBookingState(isTablesVisible = false, isTablesLoaded = false)
        }
    }

    fun getLocalTableModel(): LocalTableModel {
        val bState = bookingState.value!!
        val pState = personalState.value!!
        return LocalTableModel(
            tableNumber = bState.tableNumber.toInt(),
            date = DateFormat().convertReadableToProgrammableFormat(bState.date),
            price = bState.price,
            time = timeIn24HoursFormat,
            firstname = pState.firstname,
            lastname = pState.lastname,
            patronymic = pState.patronymic,
            completed = false
        )
    }

    private fun countAndSetPrice() {
        changeBookingState(
            price = if (!isBookingDataCorrect(indicateLocationError = false)) 0.0
            else PriceCounter(timeIn24HoursFormat).count()
        )
    }

    private fun bookTableInDatabase(model: RemoteTableModel) {
        viewModelScope.launch {
            changeNotificationState(isBookingProgressVisible = true)
            when (bookTableInRemoteDatabaseUseCase(model)) {
                Response.Progress -> {}
                Response.Error -> {
                    disableCheckWithError()
                }

                is Response.Correct -> {
                    _saveBookedTableLiveData.value = SideEffect(true)
                    clearUI()
                }
            }
            changeNotificationState(isBookingProgressVisible = false)
            _saveBookedTableLiveData.value = SideEffect(false)
        }
    }

    private fun clearUI() {
        _bookingState.value = BookingDataState()
        _personalState.value = PersonalDataState()
        _notificationState.value = NotificationDataState(isBookedNotification = true)
    }

    private fun readEmptyTables() {
        viewModelScope.launch {
            var emptyTables = mutableListOf<Int>()
            when (val result = readEmptyTablesFromRemoteDatabaseUseCase(
                bookingState.value!!.date, TimeFormat().getTimeOfDay(timeIn24HoursFormat).value
            )) {
                is TablesReadingResponse.Correct -> {
                    if (result.data.size != 12) {
                        withContext(dispatchers.defaultDispatcher) {
                            for (i in 1 until 13) {
                                if (!result.data.contains(i)) {
                                    emptyTables.add(i)
                                }
                            }
                        }
                    } else {
                        // show warning time is engaged
                    }
                }

                is TablesReadingResponse.Error -> {
                    toast(R.string.error)
                }

                is TablesReadingResponse.Empty -> {
                    emptyTables = List(12) { it + 1 }.toMutableList()
                }
            }
            changeBookingState(tables = emptyTables, isTablesLoaded = true)
        }
    }

    private fun disableCheckWithError() {
        toast(R.string.error)
    }

    private fun isBookingDataCorrect(indicateLocationError: Boolean = true): Boolean {
        return if (bookingState.value!!.date.isEmpty()) {
            if (indicateLocationError) changeBookingState(isDatePickerDialogVisible = true)
            return false
        } else if (timeIn24HoursFormat.isEmpty()) {
            if (indicateLocationError) changeBookingState(isTimePickerDialogVisible = true)
            return false
        } else {
            true
        }
    }

    private fun toast(@StringRes stringId: Int) {
        _isToastVisible.value = SideEffect(ToastModel(isVisible = true, stringId = stringId))
    }

    private val _personalState = MutableLiveData(PersonalDataState())
    val personalState: LiveData<PersonalDataState> get() = _personalState

    private val _bookingState = MutableLiveData(BookingDataState())
    val bookingState: LiveData<BookingDataState> get() = _bookingState

    private val _notificationState = MutableLiveData(NotificationDataState())
    val notificationState: LiveData<NotificationDataState> get() = _notificationState

    fun changePersonalState(
        firstname: String? = null,
        lastname: String? = null,
        patronymic: String? = null,
        isErrorFirstname: Boolean? = null,
        isErrorLastname: Boolean? = null,
        isErrorPatronymic: Boolean? = null
    ) {
        _personalState.value = personalState.value!!.copy(
            firstname = firstname ?: personalState.value!!.firstname,
            lastname = lastname ?: personalState.value!!.lastname,
            patronymic = patronymic ?: personalState.value!!.patronymic,
            isErrorFirstname = isErrorFirstname ?: personalState.value!!.isErrorFirstname,
            isErrorLastname = isErrorLastname ?: personalState.value!!.isErrorLastname,
            isErrorPatronymic = isErrorPatronymic ?: personalState.value!!.isErrorPatronymic
        )
    }

    fun changeBookingState(
        date: String? = null,
        time: String? = null,
        tableNumber: String? = null,
        isTablesVisible: Boolean? = null,
        tables: List<Int>? = null,
        isTablesLoaded: Boolean? = null,
        price: Double? = null,
        isDatePickerDialogVisible: Boolean? = null,
        isTimePickerDialogVisible: Boolean? = null
    ) {
        _bookingState.value = bookingState.value!!.copy(
            date = date ?: bookingState.value!!.date,
            time = time ?: bookingState.value!!.time,
            tableNumber = tableNumber ?: bookingState.value!!.tableNumber,
            isTablesVisible = isTablesVisible ?: bookingState.value!!.isTablesVisible,
            price = price ?: bookingState.value!!.price,
            tables = tables ?: bookingState.value!!.tables,
            isTablesLoaded = isTablesLoaded ?: bookingState.value!!.isTablesLoaded,
            isDatePickerDialogVisible = isDatePickerDialogVisible
                ?: bookingState.value!!.isDatePickerDialogVisible,
            isTimePickerDialogVisible = isTimePickerDialogVisible
                ?: bookingState.value!!.isTimePickerDialogVisible
        )
    }

    fun changeNotificationState(
        isAlertDialogVisible: WarningAlertDialogModel? = null,
        isBookingProgressVisible: Boolean? = null,
        isBookedNotification: Boolean? = null
    ) {
        _notificationState.value = notificationState.value!!.copy(
            isAlertDialogVisible = isAlertDialogVisible
                ?: notificationState.value!!.isAlertDialogVisible,
            isBookingProgressVisible = isBookingProgressVisible
                ?: notificationState.value!!.isBookingProgressVisible,
            isBookedNotification = isBookedNotification
                ?: notificationState.value!!.isBookedNotification
        )
    }

    data class PersonalDataState(
        val firstname: String = "",
        val lastname: String = "",
        val patronymic: String = "",
        val isErrorFirstname: Boolean = false,
        val isErrorLastname: Boolean = false,
        val isErrorPatronymic: Boolean = false
    )

    data class BookingDataState(
        val date: String = "",
        val time: String = "",
        val tableNumber: String = "",
        val tables: List<Int> = listOf(),
        val isTablesVisible: Boolean = false,
        val isTablesLoaded: Boolean = false,
        val price: Double = 0.0,
        val isDatePickerDialogVisible: Boolean = false,
        val isTimePickerDialogVisible: Boolean = false,
    )

    data class NotificationDataState(
        val isAlertDialogVisible: WarningAlertDialogModel = WarningAlertDialogModel(),
        val isBookingProgressVisible: Boolean = false,
        val isBookedNotification: Boolean = false
    )
}
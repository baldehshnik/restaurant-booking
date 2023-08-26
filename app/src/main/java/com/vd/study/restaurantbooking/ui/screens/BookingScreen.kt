package com.vd.study.restaurantbooking.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.ui.model.WarningAlertDialogModel
import com.vd.study.restaurantbooking.ui.screens.wigets.BookedNotificationDialog
import com.vd.study.restaurantbooking.ui.screens.wigets.BookingFloatingActionButton
import com.vd.study.restaurantbooking.ui.screens.wigets.BookingInformationCard
import com.vd.study.restaurantbooking.ui.screens.wigets.BottomSheetDatePicker
import com.vd.study.restaurantbooking.ui.screens.wigets.BottomSheetTimePicker
import com.vd.study.restaurantbooking.ui.screens.wigets.CardImageHorizontalSlider
import com.vd.study.restaurantbooking.ui.screens.wigets.DialogCircularProgress
import com.vd.study.restaurantbooking.ui.screens.wigets.PersonalInformationCard
import com.vd.study.restaurantbooking.ui.screens.wigets.WarningAlertDialog
import com.vd.study.restaurantbooking.ui.viewmodel.BookingViewModel
import com.vd.study.restaurantbooking.ui.worker.SaveBookingTableWorker

@Composable
fun BookingScreen(viewModel: BookingViewModel) {
    val owner = LocalLifecycleOwner.current
    val context = LocalContext.current
    viewModel.saveBookedTableLiveData.observe(owner) {
        if (it.get() == true) {
            val request = OneTimeWorkRequest.Builder(SaveBookingTableWorker::class.java)
                .setInputData(viewModel.getLocalTableModel().toWorkData())
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
    viewModel.isToastVisible.observe(owner) {
        val value = it.get() ?: return@observe
        if (value.isVisible) Toast.makeText(context, value.stringId, Toast.LENGTH_SHORT).show()
    }

    val personalState by viewModel.personalState.observeAsState(BookingViewModel.PersonalDataState())
    val bookingState by viewModel.bookingState.observeAsState(BookingViewModel.BookingDataState())
    val notificationState by viewModel.notificationState.observeAsState(BookingViewModel.NotificationDataState())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                CardImageHorizontalSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 12.dp),
                    state = rememberPagerState(initialPage = 1),
                    contentPadding = PaddingValues(horizontal = 40.dp),
                    items = stringArrayResource(id = R.array.restaurant_image_urls)
                )
            }

            BookingInformationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                titleId = R.string.booking_information,
                time = bookingState.time,
                date = bookingState.date,
                tables = bookingState.tables,
                isTablesVisible = bookingState.isTablesVisible,
                isTablesLoaded = bookingState.isTablesLoaded,
                onItemClick = { viewModel.changeBookingState(tableNumber = it.toString()) },
                onDateClick = { viewModel.changeBookingState(isDatePickerDialogVisible = true) },
                onTimeClick = { viewModel.changeBookingState(isTimePickerDialogVisible = true) }
            )

            PersonalInformationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 6.dp),
                titleId = R.string.personal_information,
                firstnameValue = personalState.firstname,
                lastnameValue = personalState.lastname,
                patronymicValue = personalState.patronymic,
                isErrorFirstname = personalState.isErrorFirstname,
                isErrorLastname = personalState.isErrorLastname,
                isErrorPatronymic = personalState.isErrorPatronymic,
                onFirstnameValueChange = {
                    val firstname = it.trim()
                    viewModel.changePersonalState(
                        firstname = firstname,
                        isErrorFirstname = firstname.isEmpty()
                    )
                },
                onLastnameValueChange = {
                    val lastname = it.trim()
                    viewModel.changePersonalState(
                        lastname = lastname,
                        isErrorLastname = lastname.isEmpty()
                    )
                },
                onPatronymicValueChange = {
                    val patronymic = it.trim()
                    viewModel.changePersonalState(
                        patronymic = patronymic,
                        isErrorPatronymic = patronymic.isEmpty()
                    )
                }
            )
        }

        BookingFloatingActionButton(
            modifier = Modifier.fillMaxSize(),
            price = bookingState.price,
            onClick = { viewModel.bookTable() }
        )

        if (bookingState.isTimePickerDialogVisible) {
            BottomSheetTimePicker(
                onDismiss = {
                    viewModel.changeBookingState(isTimePickerDialogVisible = false)
                },
                onConfirm = { hour, minute, is24Hour ->
                    viewModel.changeBookingState(isTimePickerDialogVisible = false)
                    viewModel.setTimeValue("$hour:$minute", is24Hour)
                }
            )
        }

        if (notificationState.isBookedNotification) {
            BookedNotificationDialog {
                viewModel.changeNotificationState(isBookedNotification = false)
            }
        }

        if (bookingState.isDatePickerDialogVisible) {
            BottomSheetDatePicker(
                onDismiss = { viewModel.changeBookingState(isDatePickerDialogVisible = false) },
                onConfirm = { viewModel.setDateValue(it) }
            )
        }

        if (notificationState.isAlertDialogVisible.isVisible) {
            WarningAlertDialog(
                modifier = Modifier.padding(bottom = 20.dp),
                contentId = notificationState.isAlertDialogVisible.stringId,
                onDismiss = {
                    viewModel.changeNotificationState(isAlertDialogVisible = WarningAlertDialogModel())
                }
            )
        }

        if (notificationState.isBookingProgressVisible) {
            DialogCircularProgress(
                properties = DialogProperties(
                    dismissOnBackPress = false, dismissOnClickOutside = false
                )
            )
        }
    }
}
package com.vd.study.restaurantbooking.ui.model

import androidx.annotation.StringRes
import com.vd.study.restaurantbooking.R

class WarningAlertDialogModel(
    val isVisible: Boolean = false,
    val showSettingsTooltip: Boolean = false,
    @StringRes val stringId: Int = R.string.error
)
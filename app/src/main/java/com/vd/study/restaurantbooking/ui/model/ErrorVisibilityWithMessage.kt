package com.vd.study.restaurantbooking.ui.model

import androidx.annotation.StringRes
import com.vd.study.restaurantbooking.R

data class ErrorVisibilityWithMessage(
    val isVisible: Boolean = false,
    @StringRes val messageId: Int = R.string.error
)
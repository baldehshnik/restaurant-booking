package com.vd.study.restaurantbooking.ui.model

import androidx.annotation.StringRes
import com.vd.study.restaurantbooking.R

data class ToastModel(
    val isVisible: Boolean = false,
    @StringRes val stringId: Int = R.string.error
)
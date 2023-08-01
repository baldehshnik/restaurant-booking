package com.vd.study.restaurantbooking.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vd.study.restaurantbooking.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val drawableId: Int
) {

    object BookingScreen : BottomNavigationScreens(
        Route.BOOKING.route, R.string.booking, R.drawable.round_add_circle_outline
    )

    object HubScreen : BottomNavigationScreens(
        Route.HUB.route, R.string.hub, R.drawable.round_home
    )
}
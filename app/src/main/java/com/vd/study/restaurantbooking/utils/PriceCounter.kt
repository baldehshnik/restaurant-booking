package com.vd.study.restaurantbooking.utils

import com.vd.study.restaurantbooking.utils.sealed.TimeOfDay

class PriceCounter(private val time: String) {

    fun count(): Double {
        return when (TimeFormat().getTimeOfDay(time)) {
            is TimeOfDay.Morning -> 8.0
            is TimeOfDay.Daytime -> 12.0
            is TimeOfDay.Evening -> 20.0
        }
    }
}
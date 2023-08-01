package com.vd.study.restaurantbooking.utils.sealed

sealed class TimeFormatVariables {
    object Correct : TimeFormatVariables()
    object Error : TimeFormatVariables()
    object LittleTime : TimeFormatVariables()
}

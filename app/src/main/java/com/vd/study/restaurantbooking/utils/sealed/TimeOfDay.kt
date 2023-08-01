package com.vd.study.restaurantbooking.utils.sealed

sealed class TimeOfDay(open val value: String) {
    class Morning(override val value: String = "MORNING"): TimeOfDay(value)
    class Daytime(override val value: String = "DAYTIME"): TimeOfDay(value)
    class Evening(override val value: String = "EVENING"): TimeOfDay(value)
}

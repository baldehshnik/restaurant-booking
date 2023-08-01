package com.vd.study.restaurantbooking.utils.sealed

import com.vd.study.restaurantbooking.domain.model.CheckTablePattern

sealed class CheckIsTableBookedResponse {
    object Error : CheckIsTableBookedResponse()
    object Empty : CheckIsTableBookedResponse()
    class Booked(val value: CheckTablePattern): CheckIsTableBookedResponse()
}
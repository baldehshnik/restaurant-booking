package com.vd.study.restaurantbooking.utils.sealed

sealed class TablesReadingResponse {
    object Error : TablesReadingResponse()
    class Correct(val data: List<Int>) : TablesReadingResponse()
    object Empty : TablesReadingResponse()
}
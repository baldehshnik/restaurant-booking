package com.vd.study.restaurantbooking.utils

class MonthConverter {
    private fun getMonth(monthNumber: Int): String {
        if (monthNumber < 1 || monthNumber > 12) {
            throw IllegalArgumentException("Value is incorrect")
        }
        return when (monthNumber) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            else -> "December"
        }
    }

    fun getFullDateAndTimeDisplayData(date: String): String {
        val (yearStr, monthStr, dayStr) = date.split("/")
        return "${getMonth(monthStr.toInt())}, $dayStr $yearStr"
    }
}
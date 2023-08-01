package com.vd.study.restaurantbooking.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
class DateFormat {
    fun convertTimeMillisToString(timeMillis: Long): String {
        return SimpleDateFormat(PROGRAMMABLE_DATE_FORMAT).format(timeMillis)
    }

    fun isDateFormatProgrammable(date: String): Boolean {
        return date.matches("\\d{4}/\\d{1,2}/\\d{1,2}".toRegex())
    }

    fun convertProgrammableToReadableFormat(date: String): String {
        val readableFormat = SimpleDateFormat(READABLE_DATE_FORMAT)
        val programmableFormat = SimpleDateFormat(PROGRAMMABLE_DATE_FORMAT)
        return readableFormat.format(programmableFormat.parse(date) ?: date)
    }

    fun convertReadableToProgrammableFormat(date: String): String {
        val readableFormat = SimpleDateFormat(READABLE_DATE_FORMAT)
        val programmableFormat = SimpleDateFormat(PROGRAMMABLE_DATE_FORMAT)
        return programmableFormat.format(readableFormat.parse(date) ?: date)
    }

    fun isDateCorrect(dateString: String): Boolean {
        val formatter = SimpleDateFormat(PROGRAMMABLE_DATE_FORMAT)
        val userDate: Date? = formatter.parse(dateString)
        val currentDate = Calendar.getInstance().time

        val calendar = Calendar.getInstance()
        calendar.set(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DATE],
            23, 59
        )
        calendar.add(Calendar.DATE, 14)

        return if (userDate == null) false
        else userDate.after(currentDate) && userDate.before(calendar.time)
    }

    companion object {
        const val PROGRAMMABLE_DATE_FORMAT = "yyyy/MM/dd"
        const val READABLE_DATE_FORMAT = "dd.MM.yyyy"
    }
}
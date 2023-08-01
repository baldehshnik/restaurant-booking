package com.vd.study.restaurantbooking.utils

import android.annotation.SuppressLint
import com.vd.study.restaurantbooking.utils.sealed.TimeFormatVariables
import com.vd.study.restaurantbooking.utils.sealed.TimeOfDay
import java.text.SimpleDateFormat
import java.util.Calendar

class TimeFormat {
    @SuppressLint("SimpleDateFormat")
    fun isTableBookingEnded(time: String, date: String): Boolean {
        val f = SimpleDateFormat("HH:mm yyyy/MM/dd")
        val dateObject = f.parse("$time $date") ?: throw IllegalArgumentException("Unknown date and time")
        val calendar = Calendar.getInstance()
        return calendar.time.after(dateObject)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeOf12HourFormat(time: String): String {
        val f = SimpleDateFormat("HH:mm")
        val date = f.parse(time) ?: throw IllegalArgumentException("Unknown time")
        return SimpleDateFormat("hh:mm a").format(date)
    }

    fun isTimeFormat(time: String): TimeFormatVariables {
        if (time.matches("\\d{1,2}:\\d{1,2}".toRegex())) {
            val (hours, minutes) = getHoursAndMinutes(time)
            return if (hours < 9 || hours > 23) {
                TimeFormatVariables.Error
            } else if ((hours == 11 || hours == 16 || hours == 22) && minutes > 0) {
                TimeFormatVariables.LittleTime
            } else if (hours == 23) {
                TimeFormatVariables.LittleTime
            } else {
                TimeFormatVariables.Correct
            }
        }

        return TimeFormatVariables.Error
    }

    fun getTimeOfDay(time: String): TimeOfDay {
        val hours = getHoursAndMinutes(time).first
        return if (hours < 12) {
            TimeOfDay.Morning()
        } else if (hours in 12..16) {
            TimeOfDay.Daytime()
        } else {
            TimeOfDay.Evening()
        }
    }

    private fun getHoursAndMinutes(time: String): Pair<Int, Int> {
        val timeDate = time.split(":")
        val hours = timeDate[0].toInt()
        val minutes = timeDate[1].toInt()
        return Pair(hours, minutes)
    }
}
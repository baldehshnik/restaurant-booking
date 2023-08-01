package com.vd.study.restaurantbooking.domain.model

import com.vd.study.restaurantbooking.data.remote.CheckRemoteTableEntity

data class CheckRemoteTableModel(
    override val date: String,
    override val time: String,
    override val tableNumber: Int
) : CheckTablePattern {

    fun toCheckBookedTableEntity(): CheckRemoteTableEntity {
        return CheckRemoteTableEntity(date, time, tableNumber)
    }
}
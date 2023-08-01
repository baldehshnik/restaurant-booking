package com.vd.study.restaurantbooking.domain.model

import com.vd.study.restaurantbooking.data.remote.RemoteTableEntity

data class RemoteTableModel(
    val tableNumber: Int,
    val date: String,
    val price: Double,
    val time: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String
) {

    fun toRemoteTableEntity(): RemoteTableEntity {
        return RemoteTableEntity(tableNumber, date, price, time, firstname, lastname, patronymic)
    }
}
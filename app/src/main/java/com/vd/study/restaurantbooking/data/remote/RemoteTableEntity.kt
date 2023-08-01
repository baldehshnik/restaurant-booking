package com.vd.study.restaurantbooking.data.remote

import com.vd.study.restaurantbooking.data.FirestoreUsage
import com.vd.study.restaurantbooking.data.NotAllowedInCode

data class RemoteTableEntity(
    val tableNumber: Int,
    val date: String,
    val price: Double,
    val time: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String
) {

    @NotAllowedInCode
    @FirestoreUsage
    private constructor() : this(-1, "", -1.0, "", "", "", "")
}
package com.vd.study.restaurantbooking.data.remote

import com.vd.study.restaurantbooking.data.NotAllowedInCode
import com.vd.study.restaurantbooking.domain.model.CheckTablePattern

data class CheckRemoteTableEntity(
    override val date: String,
    override val time: String,
    override val tableNumber: Int
): CheckTablePattern {

    @NotAllowedInCode
    private constructor(): this("", "", -1)
}
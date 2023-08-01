package com.vd.study.restaurantbooking.utils.sealed

import com.vd.study.restaurantbooking.domain.model.LocalTableModel

sealed class ReadingTableResponse {
    object Error : ReadingTableResponse()
    object Empty : ReadingTableResponse()
    class Correct(val items: List<LocalTableModel>) : ReadingTableResponse()
}
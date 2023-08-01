package com.vd.study.restaurantbooking.domain.model

import androidx.work.Data
import com.vd.study.restaurantbooking.data.local.LocalTableEntity
import com.vd.study.restaurantbooking.ui.model.LocalTableModelKeys
import com.vd.study.restaurantbooking.utils.DateFormat

data class LocalTableModel(
    val id: Int = 0,
    val tableNumber: Int,
    val date: String,
    val price: Double,
    val time: String,
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val completed: Boolean
) {

    fun toRemoteTableModel(): RemoteTableModel {
        return RemoteTableModel(
            tableNumber,
            DateFormat().convertProgrammableToReadableFormat(date),
            price,
            time,
            firstname,
            lastname,
            patronymic
        )
    }

    fun toLocalTableEntity(): LocalTableEntity {
        return LocalTableEntity(
            id,
            tableNumber,
            date,
            price,
            time,
            firstname,
            lastname,
            patronymic,
            completed
        )
    }

    fun toWorkData(): Data {
        return Data.Builder()
            .putInt(LocalTableModelKeys.ID.name, id)
            .putInt(LocalTableModelKeys.TABLE_NUMBER.name, tableNumber)
            .putString(LocalTableModelKeys.DATE.name, date)
            .putDouble(LocalTableModelKeys.PRICE.name, price)
            .putString(LocalTableModelKeys.TIME.name, time)
            .putString(LocalTableModelKeys.FIRSTNAME.name, firstname)
            .putString(LocalTableModelKeys.LASTNAME.name, lastname)
            .putString(LocalTableModelKeys.PATRONYMIC.name, patronymic)
            .putBoolean(LocalTableModelKeys.IS_COMPLETED.name, completed)
            .build()
    }
}

fun Data.toLocalTableModel(): LocalTableModel {
    return LocalTableModel(
        getInt(LocalTableModelKeys.ID.name, 0),
        getInt(LocalTableModelKeys.TABLE_NUMBER.name, 0),
        getString(LocalTableModelKeys.DATE.name) ?: "",
        getDouble(LocalTableModelKeys.PRICE.name, 0.0),
        getString(LocalTableModelKeys.TIME.name) ?: "",
        getString(LocalTableModelKeys.FIRSTNAME.name) ?: "",
        getString(LocalTableModelKeys.LASTNAME.name) ?: "",
        getString(LocalTableModelKeys.PATRONYMIC.name) ?: "",
        getBoolean(LocalTableModelKeys.IS_COMPLETED.name, true)
    )
}
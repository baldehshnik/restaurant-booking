package com.vd.study.restaurantbooking.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vd.study.restaurantbooking.domain.model.LocalTableModel

const val LOCAL_TABLE_NAME = "booked_tables"

@Entity(tableName = LOCAL_TABLE_NAME)
data class LocalTableEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "table_number")
    val tableNumber: Int,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "firstname")
    val firstname: String,

    @ColumnInfo(name = "lastname")
    val lastname: String,

    @ColumnInfo(name = "patronymic")
    val patronymic: String,

    @ColumnInfo(name = "isCompleted")
    val completed: Boolean
) {

    fun toLocalTableModel(): LocalTableModel {
        return LocalTableModel(
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
}
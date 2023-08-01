package com.vd.study.restaurantbooking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_NAME = "booked_database"

@Database(entities = [LocalTableEntity::class], version = 2, exportSchema = false)
abstract class TableRoomDatabase : RoomDatabase() {
    abstract val dao: TableDao
}
package com.vd.study.restaurantbooking.data.local

import androidx.room.*

@Dao
interface TableDao {

    @Update
    suspend fun update(entityLocal: LocalTableEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entityLocal: LocalTableEntity): Long

    @Delete
    suspend fun delete(entityLocal: LocalTableEntity): Int

    @Query("SELECT * FROM $LOCAL_TABLE_NAME")
    suspend fun readAllBookedTables(): List<LocalTableEntity>

}
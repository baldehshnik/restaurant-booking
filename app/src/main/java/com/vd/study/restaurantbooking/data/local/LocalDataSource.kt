package com.vd.study.restaurantbooking.data.local

interface LocalDataSource {
    suspend fun readAll(): List<LocalTableEntity>
    suspend fun insert(entity: LocalTableEntity): Long
    suspend fun remove(entity: LocalTableEntity): Int
    suspend fun update(entity: LocalTableEntity)
}
package com.vd.study.restaurantbooking.data.remote

interface RemoteDataSource {
    suspend fun isBooked(checkEntity: CheckRemoteTableEntity, dayOfTime: Int): CheckRemoteTableEntity?
    suspend fun book(entity: RemoteTableEntity)
    suspend fun readBookedTables(date: String, dayOfTime: String): List<Int>
    suspend fun delete(entity: RemoteTableEntity)
}
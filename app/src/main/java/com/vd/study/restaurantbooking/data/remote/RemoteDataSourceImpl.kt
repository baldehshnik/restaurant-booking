package com.vd.study.restaurantbooking.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.vd.study.restaurantbooking.di.IODispatcher
import com.vd.study.restaurantbooking.utils.TimeFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val database: FirebaseFirestore,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    override suspend fun isBooked(
        checkEntity: CheckRemoteTableEntity,
        dayOfTime: Int
    ): CheckRemoteTableEntity? {
        return withContext(ioDispatcher) {
            val worker = FirestoreCoroutineWorker(
                database.collection(DATABASE_REFERENCE)
                    .document(checkEntity.date)
                    .collection(TABLE_REFERENCE)
                    .document(checkEntity.tableNumber.toString())
                    .collection(TIME_REFERENCE)
                    .document(dayOfTime.toString()), ioDispatcher
            )

            var result: CheckRemoteTableEntity? = null
            worker.readCheckRemoteTableEntity(
                onSuccessListener = {
                    if (it != null) {
                        val value = it.toObject<CheckRemoteTableEntity>()
                        if (value != null) result = value
                    }
                }
            )

            return@withContext result
        }
    }

    override suspend fun book(entity: RemoteTableEntity) {
        return withContext(ioDispatcher) {
            val worker = FirestoreCoroutineWorker(
                database.collection(DATABASE_REFERENCE)
                    .document(entity.date)
                    .collection(DAYSTIME_REFERENCE)
                    .document(TimeFormat().getTimeOfDay(entity.time).value)
                    .collection(TABLE_REFERENCE)
                    .document(entity.tableNumber.toString()), ioDispatcher
            )
            worker.setRemoteTableEntity(entity)
        }
    }

    override suspend fun readBookedTables(date: String, dayOfTime: String): List<Int> {
        return withContext(ioDispatcher) {
            val worker = FirestoreCoroutineCollectionWorker(
                database.collection(DATABASE_REFERENCE)
                    .document(date)
                    .collection(DAYSTIME_REFERENCE)
                    .document(dayOfTime)
                    .collection(TABLE_REFERENCE), ioDispatcher
            )

            val result = mutableListOf<Int>()
            worker.read(
                onSuccessListener = { query ->
                    if (query == null) return@read
                    query.forEach { snapshot ->
                        val entity = snapshot.toObject<RemoteTableEntity>()
                        result.add(entity.tableNumber)
                    }
                }
            )

            return@withContext result
        }
    }

    override suspend fun delete(entity: RemoteTableEntity) {
        withContext(ioDispatcher) {
            val worker = FirestoreCoroutineWorker(
                database.collection(DATABASE_REFERENCE)
                    .document(entity.date)
                    .collection(DAYSTIME_REFERENCE)
                    .document(TimeFormat().getTimeOfDay(entity.time).value)
                    .collection(TABLE_REFERENCE)
                    .document(entity.tableNumber.toString()), ioDispatcher
            )
            worker.remove()
        }
    }
}
package com.vd.study.restaurantbooking.data.local

import com.vd.study.restaurantbooking.data.ReadingException
import com.vd.study.restaurantbooking.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val database: TableDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {

    override suspend fun readAll(): List<LocalTableEntity> {
        return withContext(ioDispatcher) {
            try {
                database.readAllBookedTables()
            } catch (e: Exception) {
                throw ReadingException()
            }
        }
    }

    override suspend fun insert(entity: LocalTableEntity): Long {
        return withContext(ioDispatcher) {
            database.insert(entity)
        }
    }

    override suspend fun remove(entity: LocalTableEntity): Int {
        return withContext(ioDispatcher) {
            database.delete(entity)
        }
    }

    override suspend fun update(entity: LocalTableEntity) {
        return withContext(ioDispatcher) {
            database.update(entity)
        }
    }
}
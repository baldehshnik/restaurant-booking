package com.vd.study.restaurantbooking.data.repository

import com.vd.study.restaurantbooking.data.DeleteException
import com.vd.study.restaurantbooking.data.InsertException
import com.vd.study.restaurantbooking.data.local.LocalDataSource
import com.vd.study.restaurantbooking.data.ReadingException
import com.vd.study.restaurantbooking.data.UpdateException
import com.vd.study.restaurantbooking.data.remote.RemoteDataSource
import com.vd.study.restaurantbooking.domain.model.CheckRemoteTableModel
import com.vd.study.restaurantbooking.domain.model.LocalTableModel
import com.vd.study.restaurantbooking.domain.model.RemoteTableModel
import com.vd.study.restaurantbooking.domain.repository.TableRepository
import com.vd.study.restaurantbooking.utils.sealed.CheckIsTableBookedResponse
import com.vd.study.restaurantbooking.utils.sealed.ReadingTableResponse
import com.vd.study.restaurantbooking.utils.sealed.Response
import com.vd.study.restaurantbooking.utils.sealed.TablesReadingResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : TableRepository {

    override suspend fun readAllBookingTablesFromLocalDb(): ReadingTableResponse {
        return try {
            val answer = localDataSource.readAll().map { it.toLocalTableModel() }
            if (answer.isEmpty()) {
                ReadingTableResponse.Empty
            } else {
                ReadingTableResponse.Correct(answer)
            }
        } catch (e: ReadingException) {
            ReadingTableResponse.Error
        }
    }

    override suspend fun insertBookingTableInLocalDb(entity: LocalTableModel): Response {
        return try {
            Response.Correct(localDataSource.insert(entity.toLocalTableEntity()))
        } catch (e: Exception) {
            Response.Error
        }
    }

    override suspend fun removeBookingTableFromLocalDb(entity: LocalTableModel): Response {
        return try {
            Response.Correct(localDataSource.remove(entity.toLocalTableEntity()))
        } catch (e: Exception) {
            Response.Error
        }
    }

    override suspend fun readBookedTables(date: String, dayOfTime: String): TablesReadingResponse {
        return try {
            val answer = remoteDataSource.readBookedTables(date, dayOfTime)
            if (answer.isEmpty()) {
                TablesReadingResponse.Empty
            } else {
                TablesReadingResponse.Correct(answer)
            }
        } catch (e: ReadingException) {
            TablesReadingResponse.Error
        }
    }

    override suspend fun isTableBooked(
        checkModel: CheckRemoteTableModel,
        dayOfTime: Int
    ): CheckIsTableBookedResponse {
        return try {
            val answer = remoteDataSource.isBooked(checkModel.toCheckBookedTableEntity(), dayOfTime)
            if (answer == null) {
                CheckIsTableBookedResponse.Empty
            } else {
                CheckIsTableBookedResponse.Booked(answer)
            }
        } catch (e: ReadingException) {
            CheckIsTableBookedResponse.Error
        }
    }

    override suspend fun bookTable(model: RemoteTableModel): Response {
        return try {
            remoteDataSource.book(model.toRemoteTableEntity())
            Response.Correct()
        } catch (e: InsertException) {
            Response.Error
        }
    }

    override suspend fun updateBookedTableInLocalDb(entity: LocalTableModel): Response {
        return try {
            localDataSource.update(entity.toLocalTableEntity())
            Response.Correct()
        } catch (e: UpdateException) {
            Response.Error
        }
    }

    override suspend fun deleteBookedTableFromRemoteDb(model: RemoteTableModel): Response {
        return try {
            remoteDataSource.delete(model.toRemoteTableEntity())
            Response.Correct()
        } catch (e: DeleteException) {
            Response.Error
        }
    }
}
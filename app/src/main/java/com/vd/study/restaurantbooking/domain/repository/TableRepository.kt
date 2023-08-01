package com.vd.study.restaurantbooking.domain.repository

import com.vd.study.restaurantbooking.domain.model.CheckRemoteTableModel
import com.vd.study.restaurantbooking.domain.model.LocalTableModel
import com.vd.study.restaurantbooking.domain.model.RemoteTableModel
import com.vd.study.restaurantbooking.utils.sealed.CheckIsTableBookedResponse
import com.vd.study.restaurantbooking.utils.sealed.ReadingTableResponse
import com.vd.study.restaurantbooking.utils.sealed.Response
import com.vd.study.restaurantbooking.utils.sealed.TablesReadingResponse

interface TableRepository {
    suspend fun isTableBooked(checkModel: CheckRemoteTableModel, dayOfTime: Int): CheckIsTableBookedResponse
    suspend fun bookTable(model: RemoteTableModel): Response
    suspend fun deleteBookedTableFromRemoteDb(model: RemoteTableModel): Response
    suspend fun readBookedTables(date: String, dayOfTime: String): TablesReadingResponse

    suspend fun readAllBookingTablesFromLocalDb(): ReadingTableResponse
    suspend fun insertBookingTableInLocalDb(entity: LocalTableModel): Response
    suspend fun removeBookingTableFromLocalDb(entity: LocalTableModel): Response
    suspend fun updateBookedTableInLocalDb(entity: LocalTableModel): Response
}
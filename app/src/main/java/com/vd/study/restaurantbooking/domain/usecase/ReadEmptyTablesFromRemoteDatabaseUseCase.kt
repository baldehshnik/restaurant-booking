package com.vd.study.restaurantbooking.domain.usecase

import com.vd.study.restaurantbooking.di.DefaultDispatcher
import com.vd.study.restaurantbooking.domain.repository.TableRepository
import com.vd.study.restaurantbooking.utils.sealed.TablesReadingResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReadEmptyTablesFromRemoteDatabaseUseCase @Inject constructor(
    private val repository: TableRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        date: String, dayOfTime: String
    ): TablesReadingResponse {
        return withContext(defaultDispatcher) {
            repository.readBookedTables(date, dayOfTime)
        }
    }
}
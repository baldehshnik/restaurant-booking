package com.vd.study.restaurantbooking.domain.usecase

import com.vd.study.restaurantbooking.di.DefaultDispatcher
import com.vd.study.restaurantbooking.domain.repository.TableRepository
import com.vd.study.restaurantbooking.utils.sealed.ReadingTableResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReadAllBookedTablesFromLocalDatabaseUseCase @Inject constructor(
    private val repository: TableRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): ReadingTableResponse {
        return withContext(defaultDispatcher) {
            repository.readAllBookingTablesFromLocalDb()
        }
    }
}
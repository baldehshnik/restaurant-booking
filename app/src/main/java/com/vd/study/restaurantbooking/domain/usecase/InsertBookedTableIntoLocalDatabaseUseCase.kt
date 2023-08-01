package com.vd.study.restaurantbooking.domain.usecase

import com.vd.study.restaurantbooking.di.DefaultDispatcher
import com.vd.study.restaurantbooking.domain.model.LocalTableModel
import com.vd.study.restaurantbooking.domain.repository.TableRepository
import com.vd.study.restaurantbooking.utils.sealed.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertBookedTableIntoLocalDatabaseUseCase @Inject constructor(
    private val repository: TableRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(model: LocalTableModel): Response {
        return withContext(defaultDispatcher) {
            repository.insertBookingTableInLocalDb(model)
        }
    }
}
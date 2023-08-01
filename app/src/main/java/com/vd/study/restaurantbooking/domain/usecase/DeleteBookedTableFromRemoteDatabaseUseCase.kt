package com.vd.study.restaurantbooking.domain.usecase

import com.vd.study.restaurantbooking.di.DefaultDispatcher
import com.vd.study.restaurantbooking.domain.model.RemoteTableModel
import com.vd.study.restaurantbooking.domain.repository.TableRepository
import com.vd.study.restaurantbooking.utils.sealed.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteBookedTableFromRemoteDatabaseUseCase @Inject constructor(
    private val repository: TableRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(model: RemoteTableModel): Response {
        return withContext(defaultDispatcher) {
            repository.deleteBookedTableFromRemoteDb(model)
        }
    }
}
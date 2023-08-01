package com.vd.study.restaurantbooking.ui.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vd.study.restaurantbooking.domain.model.toLocalTableModel
import com.vd.study.restaurantbooking.domain.usecase.InsertBookedTableIntoLocalDatabaseUseCase
import com.vd.study.restaurantbooking.ui.model.appComponent
import com.vd.study.restaurantbooking.utils.sealed.Response
import javax.inject.Inject

class SaveBookingTableWorker(
    context: Context, workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var insertBookedTableIntoLocalDatabaseUseCase: InsertBookedTableIntoLocalDatabaseUseCase

    override suspend fun doWork(): Result {
        applicationContext.appComponent.inject(this)
        val model = inputData.toLocalTableModel()
        return when (insertBookedTableIntoLocalDatabaseUseCase(model)) {
            is Response.Correct -> Result.failure()
            else -> Result.success()
        }
    }
}
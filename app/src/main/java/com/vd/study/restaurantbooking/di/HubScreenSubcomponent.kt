package com.vd.study.restaurantbooking.di

import com.vd.study.restaurantbooking.domain.usecase.DeleteBookedTableFromLocalDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.DeleteBookedTableFromRemoteDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.ReadAllBookedTablesFromLocalDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.UpdateBookedTableInLocalDatabaseUseCase
import com.vd.study.restaurantbooking.ui.viewmodel.HubViewModel
import com.vd.study.restaurantbooking.utils.Dispatchers
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent
@HubScreenScope
interface HubScreenSubcomponent {

    fun getViewModel(): HubViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): HubScreenSubcomponent
    }
}

@Module
object HubScreenModule {

    @Provides
    fun provideHubViewModel(
        dispatchers: Dispatchers,
        deleteBookedTableFromLocalDatabaseUseCase: DeleteBookedTableFromLocalDatabaseUseCase,
        readAllBookedTablesFromLocalDatabaseUseCase: ReadAllBookedTablesFromLocalDatabaseUseCase,
        updateBookedTableInLocalDatabaseUseCase: UpdateBookedTableInLocalDatabaseUseCase,
        deleteBookedTableFromRemoteDatabaseUseCase: DeleteBookedTableFromRemoteDatabaseUseCase
    ): HubViewModel {
        return HubViewModel(
            dispatchers,
            deleteBookedTableFromLocalDatabaseUseCase,
            readAllBookedTablesFromLocalDatabaseUseCase,
            updateBookedTableInLocalDatabaseUseCase,
            deleteBookedTableFromRemoteDatabaseUseCase
        )
    }
}
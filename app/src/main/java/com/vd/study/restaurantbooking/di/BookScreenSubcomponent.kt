package com.vd.study.restaurantbooking.di

import com.vd.study.restaurantbooking.domain.usecase.BookTableInRemoteDatabaseUseCase
import com.vd.study.restaurantbooking.domain.usecase.ReadEmptyTablesFromRemoteDatabaseUseCase
import com.vd.study.restaurantbooking.ui.viewmodel.BookingViewModel
import com.vd.study.restaurantbooking.utils.Dispatchers
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [BookScreenModule::class])
@BookScreenScope
interface BookScreenSubcomponent {
    fun getViewModel(): BookingViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): BookScreenSubcomponent
    }
}

@Module
object BookScreenModule {

    @Provides
    fun provideBookViewModel(
        dispatcher: Dispatchers,
        bookTableInRemoteDatabaseUseCase: BookTableInRemoteDatabaseUseCase,
        readEmptyTablesFromRemoteDatabaseUseCase: ReadEmptyTablesFromRemoteDatabaseUseCase
    ): BookingViewModel {
        return BookingViewModel(
            dispatcher,
            bookTableInRemoteDatabaseUseCase,
            readEmptyTablesFromRemoteDatabaseUseCase
        )
    }
}
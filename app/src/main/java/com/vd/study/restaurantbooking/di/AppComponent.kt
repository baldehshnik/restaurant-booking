package com.vd.study.restaurantbooking.di

import android.content.Context
import com.vd.study.restaurantbooking.ui.MainActivity
import com.vd.study.restaurantbooking.ui.worker.SaveBookingTableWorker
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, AppBindModule::class])
@Singleton
interface AppComponent {

    fun createBookScreenSubcomponent(): BookScreenSubcomponent.Builder
    fun createHubScreenSubcomponent(): HubScreenSubcomponent.Builder

    fun inject(activity: MainActivity)
    fun inject(worker: SaveBookingTableWorker)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
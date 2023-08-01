package com.vd.study.restaurantbooking.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vd.study.restaurantbooking.data.local.DATABASE_NAME
import com.vd.study.restaurantbooking.data.local.TableDao
import com.vd.study.restaurantbooking.data.local.TableRoomDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Singleton

@Module(subcomponents = [BookScreenSubcomponent::class, HubScreenSubcomponent::class])
object AppModule {

    @Provides
    @Singleton
    fun provideTableRoomDatabase(context: Context): TableRoomDatabase {
        return Room.databaseBuilder(
            context,
            TableRoomDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFirestoreDatabase(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun provideTableDao(database: TableRoomDatabase): TableDao {
        return database.dao
    }

    @Provides
    @MainDispatcher
    fun provideMainCoroutineDispatcher(): MainCoroutineDispatcher {
        return Dispatchers.Main
    }

    @Provides
    @DefaultDispatcher
    fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    @IODispatcher
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @UnconfinedDispatcher
    fun provideUnconfinedCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}
package com.vd.study.restaurantbooking.di

import com.vd.study.restaurantbooking.data.local.LocalDataSource
import com.vd.study.restaurantbooking.data.local.LocalDataSourceImpl
import com.vd.study.restaurantbooking.data.remote.RemoteDataSource
import com.vd.study.restaurantbooking.data.remote.RemoteDataSourceImpl
import com.vd.study.restaurantbooking.domain.repository.TableRepository
import com.vd.study.restaurantbooking.data.repository.TableRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
@Suppress("FunctionName")
interface AppBindModule {
    @Binds
    fun bindTableRepositoryImpl_to_TableRepository(repositoryImpl: TableRepositoryImpl): TableRepository

    @Binds
    fun bindLocalDataSourceImpl_to_LocalDataSource(sourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    fun bindRemoteDataSourceImpl_to_RemoteDataSource(sourceImpl: RemoteDataSourceImpl): RemoteDataSource
}
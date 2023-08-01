package com.vd.study.restaurantbooking.di

import javax.inject.Qualifier
import javax.inject.Scope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IODispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UnconfinedDispatcher

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BookScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class HubScreenScope
package com.vd.study.restaurantbooking.utils

import com.vd.study.restaurantbooking.di.DefaultDispatcher
import com.vd.study.restaurantbooking.di.IODispatcher
import com.vd.study.restaurantbooking.di.MainDispatcher
import com.vd.study.restaurantbooking.di.UnconfinedDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Dispatchers @Inject constructor(

    @MainDispatcher
    private val _mainDispatcher: MainCoroutineDispatcher,

    @IODispatcher
    private val _ioDispatcher: CoroutineDispatcher,

    @DefaultDispatcher
    private val _defaultDispatcher: CoroutineDispatcher,

    @UnconfinedDispatcher
    private val _unconfinedDispatcher: CoroutineDispatcher,
) {

    val mainDispatcher: MainCoroutineDispatcher get() = _mainDispatcher
    val ioDispatcher: CoroutineDispatcher get() = _ioDispatcher
    val defaultDispatcher: CoroutineDispatcher get() = _defaultDispatcher
    val unconfinedDispatcher: CoroutineDispatcher get() = _unconfinedDispatcher

}
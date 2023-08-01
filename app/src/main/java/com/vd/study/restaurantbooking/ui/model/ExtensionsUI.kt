package com.vd.study.restaurantbooking.ui.model

import android.content.Context
import androidx.compose.foundation.pager.PagerState
import com.vd.study.restaurantbooking.App
import com.vd.study.restaurantbooking.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }

fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction
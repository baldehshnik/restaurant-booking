package com.vd.study.restaurantbooking.utils

class SideEffect<T>(_value: T) {
    private var value: T? = _value
    fun get() = value.also { value = null }
}
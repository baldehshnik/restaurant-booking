package com.vd.study.restaurantbooking.utils.sealed

sealed class Response {
    object Progress : Response()
    object Error : Response()
    class Correct() : Response() {

        var value: Any? = null

        constructor(_value: Any) : this() {
            value = _value
        }
    }
}
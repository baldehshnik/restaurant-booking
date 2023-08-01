package com.vd.study.restaurantbooking.data

/**
 * The annotation is used to prohibit the use of a function, a class or a variable to write code.
 * */
@Retention(AnnotationRetention.SOURCE)
annotation class NotAllowedInCode

/**
 * The annotation is used to show that a code of block is only used by Firestore.
 * */
@Retention(AnnotationRetention.SOURCE)
annotation class FirestoreUsage
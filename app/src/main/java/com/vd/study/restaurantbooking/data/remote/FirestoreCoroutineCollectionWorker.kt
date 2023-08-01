package com.vd.study.restaurantbooking.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.vd.study.restaurantbooking.data.ReadingException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FirestoreCoroutineCollectionWorker(
    private val collection: CollectionReference,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun read(
        onSuccessListener: ((QuerySnapshot?) -> Unit)? = null,
        onFailureListener: ((Exception) -> Unit)? = null
    ) {
        withContext(ioDispatcher) {
            var isFinished = false
            var isError = false

            collection.get()
                .addOnSuccessListener { query ->
                    onSuccessListener?.let { it(query) }
                    isFinished = true
                }
                .addOnFailureListener { e ->
                    onFailureListener?.let { it(e) }
                    isError = true
                }

            while (!isFinished && !isError) delay(50L)
            if (isError) throw ReadingException()
        }
    }
}
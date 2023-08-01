package com.vd.study.restaurantbooking.data.remote

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.vd.study.restaurantbooking.data.DeleteException
import com.vd.study.restaurantbooking.data.InsertException
import com.vd.study.restaurantbooking.data.ReadingException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FirestoreCoroutineWorker(
    private val document: DocumentReference,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun setRemoteTableEntity(
        entity: RemoteTableEntity,
        onSuccessListener: (() -> Unit)? = null,
        onFailureListener: ((Exception) -> Unit)? = null
    ) {
        withContext(ioDispatcher) {
            var isFinished = false
            var isError = false
            document.set(entity)
                .addOnSuccessListener {
                    onSuccessListener?.let { listener -> listener() }
                    isFinished = true
                }.addOnFailureListener {
                    onFailureListener?.let { listener -> listener(it) }
                    isError = true
                }

            while (!isFinished && !isError) delay(50L)
            if (isError) throw InsertException()
        }
    }

    suspend fun readCheckRemoteTableEntity(
        onSuccessListener: ((DocumentSnapshot?) -> Unit)? = null,
        onFailureListener: ((Exception) -> Unit)? = null
    ) {
        withContext(ioDispatcher) {
            var isFinished = false
            var isError = false
            document.get()
                .addOnSuccessListener {
                    onSuccessListener?.let { listener -> listener(it) }
                    isFinished = true
                }.addOnFailureListener {
                    onFailureListener?.let { listener -> listener(it) }
                    isError = true
                }

            while (!isFinished && !isError) delay(50L)
            if (isError) throw ReadingException()
        }
    }

    suspend fun remove(
        onSuccessListener: (() -> Unit)? = null,
        onFailureListener: ((Exception) -> Unit)? = null
    ) {
        withContext(ioDispatcher) {
            var isFinished = false
            var isError = false
            document.delete()
                .addOnSuccessListener {
                    onSuccessListener?.let { listener -> listener() }
                    isFinished = true
                }.addOnFailureListener {
                    onFailureListener?.let { listener -> listener(it) }
                    isError = true
                }

            while (!isFinished && !isError) delay(50L)
            if (isError) throw DeleteException()
        }
    }
}
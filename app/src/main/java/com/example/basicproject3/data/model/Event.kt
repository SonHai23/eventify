package com.example.basicproject3.data.model

import android.net.Uri
import android.os.Parcelable
import com.example.basicproject3.data.Utils.Companion.currentTimestamp
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    @DocumentId val id: String? = null,
    val host: String? = null,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val location: String? = null,
    val date_start: Timestamp? = null,
    val date_end: Timestamp? = null
) : Parcelable {

    @IgnoredOnParcel
    private val db = Firebase.firestore

    fun getImgUrl(): Task<Uri> {
        val storage = FirebaseStorage.getInstance()
        val imgRef = storage.getReference("events/$id")
        return imgRef.downloadUrl
    }

    suspend fun getOrganizer(uid: String): Organizer? {
        val querySnapshot = db.collection("users").document(uid).get().await()
        return querySnapshot.toObject(Organizer::class.java)
    }

    @IgnoredOnParcel
    private val _ticketCollection = fun(): CollectionReference {
        return db.collection("events/$id/tickets")
    }

    @IgnoredOnParcel
    private val ticketCollection = _ticketCollection()

    suspend fun hasTicket(currentUserId: String): Boolean {
        return !ticketCollection.whereEqualTo("uid", currentUserId).get().await().isEmpty
    }

    suspend fun buyTicket(): Ticket {
        val currentUserId = User().uid
        val ticket = Ticket(uid = currentUserId, eid = id, date_purchased = currentTimestamp())
        val documentReference = ticketCollection.add(ticket).await()
        val documentSnapshot = documentReference.get().await()
        return documentSnapshot.toObject(Ticket::class.java)!!
    }
}
package com.example.basicproject3.data.model

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class User() {
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()
    val uid = auth.currentUser?.uid

    fun getUsername(): Task<DocumentSnapshot> {
        val db = Firebase.firestore
//        val uid = auth.currentUser?.uid
        val docRef = db.collection("users").document(uid!!)

        return docRef.get(Source.CACHE)
    }

    fun getAvatar(): Task<Uri> {
        val storageRef = storage.reference.child("profiles/${uid}")
        return storageRef.downloadUrl
    }

    suspend fun getTickets(): List<Ticket> {
        val db = Firebase.firestore
        val docRef = db.collectionGroup("tickets").whereEqualTo("uid", uid).get().await()
        val tickets = mutableListOf<Ticket>()
        for (document in docRef) {
            tickets.add(document.toObject(Ticket::class.java))
        }
        return tickets
    }

    suspend fun getEvents(): List<Event> {
        val db = Firebase.firestore
        val docRef = db.collection("events").whereEqualTo("host", uid).get().await()
        val events = mutableListOf<Event>()
        for (document in docRef) {
            events.add(document.toObject(Event::class.java))
        }
        return events
    }
}
package com.example.mytufit

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FavoriteManager {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun userId(): String? = auth.currentUser?.uid

    // Add to favorites
    fun addToFavorites(itemId: String, type: String) {
        val uid = userId() ?: return
        val docRef = db.collection("users").document(uid).collection("favorites").document(itemId)
        val data = mapOf("type" to type)
        docRef.set(data)
    }

    // Remove from favorites
    fun removeFromFavorites(itemId: String) {
        val uid = userId() ?: return
        val docRef = db.collection("users").document(uid).collection("favorites").document(itemId)
        docRef.delete()
    }

    // Check if item is favorited
    fun isFavorite(itemId: String, onResult: (Boolean) -> Unit) {
        val uid = userId() ?: return onResult(false)
        val docRef = db.collection("users").document(uid).collection("favorites").document(itemId)
        docRef.get().addOnSuccessListener { snapshot ->
            onResult(snapshot.exists())
        }.addOnFailureListener {
            onResult(false)
        }
    }
}

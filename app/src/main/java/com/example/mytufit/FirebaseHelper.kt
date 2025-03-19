package com.example.mytufit

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// A simple data class for a user.
data class User(val name: String? = null, val email: String? = null)

// FirebaseHelper encapsulates read/write operations to Firebase Realtime Database.
class FirebaseHelper {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    // Write a new user under "users/{userId}"
    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        database.child("users").child(userId).setValue(user)
    }

    // Read user data from "users/{userId}"
    fun readUserData(userId: String, onDataChange: (User?) -> Unit) {
        database.child("users").child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    onDataChange(user)
                }
                override fun onCancelled(error: DatabaseError) {
                    onDataChange(null)
                }
            })
    }
}

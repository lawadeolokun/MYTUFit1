package com.example.mytufit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class LoadTestingFragment : Fragment(R.layout.fragment_load_testing) {

    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()

        val btnGenerate = view.findViewById<Button>(R.id.btnLoadTesting)
        btnGenerate.setOnClickListener {
            generateLoadTestingPosts()
        }
    }

    private fun generateLoadTestingPosts() {
        val topicName = "General Chat"  // Change if you want to test other topics

        for (i in 1..1000) {
            val postId = UUID.randomUUID().toString()
            val post = hashMapOf(
                "postId" to postId,
                "topic" to topicName,
                "userId" to "testUser$i",
                "title" to "Test Post #$i",
                "body" to "This is fake body text for load testing. Post number $i.",
                "timestamp" to System.currentTimeMillis(),
                "likes" to emptyList<String>()
            )

            firestore.collection("topics")
                .document(topicName)
                .collection("posts")
                .document(postId)
                .set(post)
                .addOnSuccessListener {
                    Log.d("Random", "Inserted post $i")
                }
                .addOnFailureListener {
                    Log.e("Random", "Failed to insert post $i: ${it.message}")
                }
        }

        Toast.makeText(requireContext(), "Generating 1000 fake posts...", Toast.LENGTH_LONG).show()
    }
}
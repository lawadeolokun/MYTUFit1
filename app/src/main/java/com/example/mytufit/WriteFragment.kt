package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class WriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_write, container, false)


        val topicName = arguments?.getString("topicName") ?: "General Chat"
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etBody = view.findViewById<EditText>(R.id.etBody)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString()
            val body = etBody.text.toString()
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val timestamp = System.currentTimeMillis()

            val database = FirebaseDatabase.getInstance().reference
            val postId = database.child("topics").child(topicName).child("posts").push().key

            if (postId != null && userId != null) {
                val newPost = Post(
                    postId = postId,
                    title = title,
                    body = body,
                    userId = userId,
                    timestamp = timestamp
                )
                database.child("topics").child(topicName).child("posts").child(postId).setValue(newPost)
                    .addOnCompleteListener {
                        // After submission, return to the TopicDetailFragment
                        findNavController().popBackStack()
                    }
            }
        }

        return view
    }
}
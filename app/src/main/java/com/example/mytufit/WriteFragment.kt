package com.example.mytufit

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WriteFragment : Fragment() {

    private lateinit var etTitle: EditText
    private lateinit var etMessage: EditText
    private lateinit var btnSubmit: Button
    private lateinit var database: DatabaseReference
    private var topicName: String = "General Chat"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the write post layout
        val view = inflater.inflate(R.layout.fragment_write, container, false)

        etTitle = view.findViewById(R.id.etTitle)
        etMessage = view.findViewById(R.id.etMessage)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        database = FirebaseDatabase.getInstance().reference

        // Retrieve the topic name from arguments passed by TopicDetailFragment
        topicName = arguments?.getString("topicName") ?: "General Chat"

        btnSubmit.setOnClickListener {
            val titleText = etTitle.text.toString().trim()
            val messageText = etMessage.text.toString().trim()

            if (titleText.isEmpty() || messageText.isEmpty()) {
                Toast.makeText(context, "Please enter both a title and a message", Toast.LENGTH_SHORT).show()
            } else {
                writePost(topicName, titleText, messageText)
            }
        }


        return view
    }

    private fun writePost(topic: String, titleText: String, messageText: String) {
        // Create a new key for the post under topics/<topic>/posts
        val postId = database.child("topics").child(topic).child("posts").push().key

        Log.d("WritePost", "Generated postId: $postId") //DEBUG

        if (postId == null) {
            Toast.makeText(context, "Error posting. Try again.", Toast.LENGTH_SHORT).show()
            return
        }
        // Create the Post object with all required fields
        val newPost = Post(
            postId = postId,
            topic = topic,   // from arguments
            title = titleText,   // from etTitle
            body = messageText,  // from etMessage
            likes = emptyMap()
        )

        Log.d("WritePost", "Writing to Firebase: $newPost")
        // Save the post in Firebase under topics/<topic>/posts/<postId>
        database.child("topics").child(topic).child("posts").child(postId)
            .setValue(newPost)
            .addOnSuccessListener {
                Log.d("WritePost", "POST SUCCESS!")
                Toast.makeText(context, "Post submitted successfully!", Toast.LENGTH_SHORT).show()
                etTitle.text.clear()
                etMessage.text.clear()
                findNavController().popBackStack()
            }
            .addOnFailureListener {
                Log.e("WritePost", "POST FAILED: ${it.message}")
                Toast.makeText(context, "Failed: ${it.message}", Toast.LENGTH_LONG).show()
            }

    }
}

package com.example.mytufit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WriteFragment : Fragment() {

    private lateinit var etTitle: EditText
    private lateinit var etMessage: EditText
    private lateinit var btnSubmit: Button
    private lateinit var firestore: FirebaseFirestore
    private var topicName: String = "General Chat"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_write, container, false)

        // Show a toast to confirm the fragment is being loaded
        Toast.makeText(requireContext(), "WriteFragment loaded", Toast.LENGTH_SHORT).show()

        // Initialize views
        etTitle = view.findViewById(R.id.etTitle)
        etMessage = view.findViewById(R.id.etMessage)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        // Init Firestore
        firestore = FirebaseFirestore.getInstance()

        // Get the topic name from arguments
        topicName = arguments?.getString("topicName") ?: "General Chat"

        btnSubmit.setOnClickListener {
            Log.d("WriteFragment", "Submit button clicked")

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
        val postId = firestore.collection("topics")
            .document(topic)
            .collection("posts")
            .document()
            .id  // Generate unique Firestore ID

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"

        val newPost = Post(
            postId = postId,
            topic = topic,
            userId = userId,
            title = titleText,
            body = messageText,
            timestamp = System.currentTimeMillis(),
            likes = emptyList()
        )

        Toast.makeText(context, "Submitting post...", Toast.LENGTH_SHORT).show()

        firestore.collection("topics")
            .document(topic)
            .collection("posts")
            .document(postId)
            .set(newPost)
            .addOnSuccessListener {
                Log.d("WriteFragment", "Post uploaded to Firestore")
                Toast.makeText(context, "✅ Post submitted!", Toast.LENGTH_SHORT).show()
                etTitle.text.clear()
                etMessage.text.clear()
                findNavController().popBackStack()
            }
            .addOnFailureListener {
                Log.e("WriteFragment", "❌ Failed to write post: ${it.message}")
                Toast.makeText(context, "Failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}

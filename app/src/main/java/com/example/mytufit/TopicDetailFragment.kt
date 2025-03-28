package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class TopicDetailFragment :Fragment() {

    private lateinit var rvPosts: RecyclerView
    private lateinit var btnNewPost: Button
    private lateinit var tvTopicTitle: TextView

    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<Post>()

    private lateinit var firestore: FirebaseFirestore
    private var listenerRegistration: ListenerRegistration? = null
    private lateinit var topicName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_topic_detail, container, false)

        tvTopicTitle = view.findViewById(R.id.tvTopicTitle)
        rvPosts = view.findViewById(R.id.rvPosts)
        btnNewPost = view.findViewById(R.id.btnNewPost)

        // Retrieve topic from arguments (passed from CommunityFragment)
        topicName = arguments?.getString("topicName") ?: "General Chat"
        tvTopicTitle.text = topicName

        // Setup Firestore
        firestore = FirebaseFirestore.getInstance()

        // Setup RecyclerView
        rvPosts.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(postList, topicName)  // Pass the second argument here
        rvPosts.adapter = postAdapter

        // Load posts from Firestore
        listenForPosts()


        // New Post button: navigate to WriteFragment, passing the topic name
        btnNewPost.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", topicName) }
            findNavController().navigate(R.id.action_topicDetailFragment_to_writeFragment, bundle)
        }

        return view
    }
    private fun listenForPosts() {
        listenerRegistration = firestore.collection("topics")
            .document(topicName)
            .collection("posts")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                postList.clear()
                for (doc in snapshot.documents) {
                    val post = doc.toObject(Post::class.java)
                    post?.let { postList.add(it) }
                }
                postAdapter.notifyDataSetChanged()
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        listenerRegistration?.remove()
    }
}
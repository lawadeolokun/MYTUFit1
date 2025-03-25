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

class TopicDetailFragment :Fragment() {

    private lateinit var rvPosts: RecyclerView
    private lateinit var btnNewPost: Button
    private lateinit var tvTopicTitle: TextView
    private lateinit var database: DatabaseReference
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_topic_detail, container, false)

        tvTopicTitle = view.findViewById(R.id.tvTopicTitle)
        rvPosts = view.findViewById(R.id.rvPosts)
        btnNewPost = view.findViewById(R.id.btnNewPost)

        // Retrieve topic from arguments (passed from CommunityFragment)
        val topicName = arguments?.getString("topicName") ?: "General Chat"
        tvTopicTitle.text = topicName

        // Setup RecyclerView
        rvPosts.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(postList, topicName)  // Pass the second argument here
        rvPosts.adapter = postAdapter

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference

        // Query Firebase for posts under the selected topic
        database.child("topics").child(topicName).child("posts")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    postList.clear()
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(Post::class.java)
                        post?.let { postList.add(it) }
                    }
                    postAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })

        // New Post button: navigate to WriteFragment, passing the topic name
        btnNewPost.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", topicName) }
            findNavController().navigate(R.id.action_topicDetailFragment_to_writeFragment, bundle)
        }

        return view
    }

}
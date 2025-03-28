package com.example.mytufit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

class PostAdapter(
    private var posts: MutableList<Post>,
    private val topicName: String  // Posts are stored under topics
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)

        // Set like count and update icon based on whether the current user has liked the post
        val likeCount = post.likes?.size ?: 0
        holder.tvLikeCount.text = likeCount.toString()

        val currentUserId = auth.currentUser?.uid
        val hasLiked = currentUserId != null && post.likes?.containsKey(currentUserId) == true

        if (hasLiked) {
            holder.btnLike.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            holder.btnLike.setImageResource(android.R.drawable.btn_star_big_off)
        }

        // Like button toggle
        holder.btnLike.setOnClickListener {
            val postRef = firestore.collection("topics")
                .document(topicName)
                .collection("posts")
                .document(post.postId ?: return@setOnClickListener)

            if (hasLiked) {
                postRef.update("likes", FieldValue.arrayRemove(currentUserId))
            } else {
                postRef.update("likes", FieldValue.arrayUnion(currentUserId))
            }
        }
    }

    override fun getItemCount() = posts.size

    fun updatePosts(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvBody: TextView = itemView.findViewById(R.id.tvBody)
        val btnLike: ImageButton = itemView.findViewById(R.id.btnLike)
        val tvLikeCount: TextView = itemView.findViewById(R.id.tvLikeCount)

        fun bind(post: Post) {
            tvTitle.text = post.title
            tvBody.text = post.body ?: ""
        }
    }
}

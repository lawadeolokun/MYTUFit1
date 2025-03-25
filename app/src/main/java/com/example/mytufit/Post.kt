package com.example.mytufit

data class Post(
    val postId: String? = null,
    val title: String? = null,
    val body: String? = null,
    val userId: String? = null,
    val timestamp: Long? = null,
    val likes: Map<String, Boolean>? = null,  // Each userId that liked the post
    val comments: Map<String, Comment>? = null, // Comments keyed by commentId
    val favorite: Boolean? = false // For bookmarking - can be stored per user instead
)


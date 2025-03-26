package com.example.mytufit

data class Post(
    val postId: String? = null,
    val topic: String = "",
    val message: String = "",
    val title: String = "",
    val body: String = "",
    val likes: Map<String, Boolean>? = null
)

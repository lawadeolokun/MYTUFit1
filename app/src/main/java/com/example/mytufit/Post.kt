package com.example.mytufit

data class Post(
    val postId: String? = null,
    val topic: String = "",
    val userId: String = "",
    val title: String = "",
    val body: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val likes: List<String> = emptyList(),
)

package com.example.mytufit

data class Comment(
    val commentId: String? = null,
    val text: String? = null,
    val userId: String? = null,
    val timestamp: Long? = null,
    val replies: Map<String, Comment>? = null
)

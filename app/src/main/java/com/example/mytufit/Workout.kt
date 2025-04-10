package com.example.mytufit

data class Workout(
    val name: String = "",
    val category: String = "",
    val level: String = "",
    val videoUrl: String = "",
    val description: String = "",
    val reps: Int = 0,
    val sets: Int = 0
)

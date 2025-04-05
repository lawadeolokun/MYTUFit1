package com.example.mytufit

data class Drink(
    val name: String = "",
    val type: String = "",
    val calories: Int = 0,
    val ingredients: List<String> = emptyList(),
    val directions: String = "",
    val imageUrl: String = ""
)

package com.example.mytufit

data class DrinkPlan(
    val name: String = "",
    val category: String = "",
    val calories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val duration: String = "",
    val ingredients: List<String> = emptyList(),
    val directions: String = "",
    val imageUrl: String = ""
)

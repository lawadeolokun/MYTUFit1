package com.example.mytufit

// This is a data class that matches Firestore meal document fields
data class MealPlan(
    val name: String = "",
    val category: String = "",
    val calories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val duration: String = "",
    val directions: String = "",
    val ingredients: List<String> = emptyList(),
    val imageUrl: String = "",
    val docId: String = "",
    val isFavorite: Boolean = false
) {
    constructor() : this(
        name = "",
        category = "",
        calories = 0,
        protein = 0,
        carbs = 0,
        duration = "",
        directions = "",
        ingredients = listOf(),
        imageUrl = "",
        docId = "",
        isFavorite = false
    )
}


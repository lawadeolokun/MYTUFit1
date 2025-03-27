package com.example.mytufit

// This is a data class that matches Firestore meal document fields
data class MealPlan(
    val name: String = "",                // Name of the meal
    val category: String = "",            // Category (Omnivorous, Vegan, Spicy...)
    val calories: Int = 0,                // Calorie count
    val protein: Int = 0,                 // Protein grams
    val carbs: Int = 0,                   // Carbs grams
    val duration: String = "",            // Estimated cooking time
    val directions: String = "",          // Cooking instructions
    val ingredients: List<String> = emptyList(), // Ingredients list
    val imageUrl: String = ""             // (Optional) URL to meal image
)

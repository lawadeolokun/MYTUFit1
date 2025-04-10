package com.example.mytufit

import android.widget.ImageView
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MealPlanAdapter(private val meals: List<MealPlan>) :
    RecyclerView.Adapter<MealPlanAdapter.MealViewHolder>() {

    // ViewHolder holds references to UI elements in each card
    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMealName: TextView = itemView.findViewById(R.id.tvMealName)
        val tvMealCategory: TextView = itemView.findViewById(R.id.tvMealCategory)
        val ivMealImage: ImageView = itemView.findViewById(R.id.ivMealImage)
        val tvIngredients: TextView = itemView.findViewById(R.id.tvIngredients)
        val tvDirections: TextView = itemView.findViewById(R.id.tvDirections)
        val tvCalories: TextView = itemView.findViewById(R.id.tvCalories)
        val tvProtein: TextView = itemView.findViewById(R.id.tvProtein)
        val tvCarbs: TextView = itemView.findViewById(R.id.tvCarbs)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        val tvReference: TextView = itemView.findViewById(R.id.tvReference)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal_plan, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.tvMealName.text = meal.name
        holder.tvMealCategory.text = meal.category
        holder.tvIngredients.text = "Ingredients:\n${meal.ingredients.joinToString("\n")}"
        holder.tvDirections.text = "Directions:\n${meal.directions}"
        holder.tvCalories.text = "${meal.calories} kcal"
        holder.tvProtein.text = "Protein ${meal.protein}g"
        holder.tvCarbs.text = "Carbs ${meal.carbs}g"
        holder.tvDuration.text = meal.duration

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(meal.imageUrl)
            .into(holder.ivMealImage)
    }


    override fun getItemCount() = meals.size
}

package com.example.mytufit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DrinkPlanAdapter(private val drinkList: List<DrinkPlan>) :
    RecyclerView.Adapter<DrinkPlanAdapter.DrinkViewHolder>() {

    class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivDrinkImage: ImageView = itemView.findViewById(R.id.ivMealImage)
        val tvName: TextView = itemView.findViewById(R.id.tvMealName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvMealCategory)
        val tvCalories: TextView = itemView.findViewById(R.id.tvCalories)
        val tvProtein: TextView = itemView.findViewById(R.id.tvProtein)
        val tvCarbs: TextView = itemView.findViewById(R.id.tvCarbs)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        val tvIngredients: TextView = itemView.findViewById(R.id.tvIngredients)
        val tvDirections: TextView = itemView.findViewById(R.id.tvDirections)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal_plan, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinkList[position]
        holder.tvName.text = drink.name
        holder.tvCategory.text = drink.category
        holder.tvCalories.text = "${drink.calories} kcal"
        holder.tvProtein.text = "Protein ${drink.protein}g"
        holder.tvCarbs.text = "Carbs ${drink.carbs}g"
        holder.tvDuration.text = drink.duration
        holder.tvIngredients.text = "Ingredients:\n${drink.ingredients.joinToString("\n")}"
        holder.tvDirections.text = "Directions:\n${drink.directions}"
        Glide.with(holder.itemView.context).load(drink.imageUrl).into(holder.ivDrinkImage)
    }

    override fun getItemCount() = drinkList.size
}

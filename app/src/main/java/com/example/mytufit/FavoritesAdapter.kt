package com.example.mytufit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavoritesAdapter(
    private var favorites: List<MealPlan>
) : RecyclerView.Adapter<FavoritesAdapter.FavViewHolder>() {

    class FavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mealImage: ImageView = view.findViewById(R.id.ivMealImage)
        val mealName: TextView = view.findViewById(R.id.tvMealName)
        val mealCategory: TextView = view.findViewById(R.id.tvMealCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal_plan, parent, false)
        return FavViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val item = favorites[position]
        holder.mealName.text = item.name
        holder.mealCategory.text = item.category
        Glide.with(holder.itemView).load(item.imageUrl).into(holder.mealImage)
    }

    override fun getItemCount() = favorites.size

    fun updateList(newList: List<MealPlan>) {
        favorites = newList
        notifyDataSetChanged()
    }
}

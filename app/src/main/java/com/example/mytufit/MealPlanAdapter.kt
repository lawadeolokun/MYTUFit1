package com.example.mytufit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// MealPlanAdapter with updateList support for Smart Search
class MealPlanAdapter(private var mealList: List<MealPlan>) :
    RecyclerView.Adapter<MealPlanAdapter.MealViewHolder>() {

    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val db = FirebaseFirestore.getInstance()

    // ViewHolder class holds the views for each meal item
    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMealImage: ImageView = itemView.findViewById(R.id.ivMealImage)
        val tvName: TextView = itemView.findViewById(R.id.tvMealName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvMealCategory)
        val tvCalories: TextView = itemView.findViewById(R.id.tvCalories)
        val tvProtein: TextView = itemView.findViewById(R.id.tvProtein)
        val tvCarbs: TextView = itemView.findViewById(R.id.tvCarbs)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        val tvIngredients: TextView = itemView.findViewById(R.id.tvIngredients)
        val tvDirections: TextView = itemView.findViewById(R.id.tvDirections)
        val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal_plan, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]

        // Stop if no user or no docId
        val uid = userId ?: return
        val docId = meal.docId.ifEmpty { return }

        // Bind all views with meal data
        holder.tvName.text = meal.name
        holder.tvCategory.text = meal.category
        holder.tvCalories.text = "${meal.calories} kcal"
        holder.tvProtein.text = "${meal.protein}g Protein"
        holder.tvCarbs.text = "${meal.carbs}g Carbs"
        holder.tvDuration.text = meal.duration
        holder.tvIngredients.text = meal.ingredients.joinToString("\n")
        holder.tvDirections.text = meal.directions

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(meal.imageUrl)
            .into(holder.ivMealImage)

        // Favorites: Firestore reference to this user's favorite meal
        val favDoc = db.collection("users").document(uid)
            .collection("favorites").document(docId)

        // Load favorite icon based on whether this doc exists
        favDoc.get().addOnSuccessListener {
            val isFav = it.exists()
            holder.btnFavorite.setImageResource(
                if (isFav) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            )
        }

        // Toggle favorite on heart click
        holder.btnFavorite.setOnClickListener {
            favDoc.get().addOnSuccessListener { doc ->
                if (doc.exists()) {
                    // Remove from favorites
                    favDoc.delete()
                    holder.btnFavorite.setImageResource(R.drawable.ic_heart_outline)
                } else {
                    // Add to favorites
                    favDoc.set(meal)
                    holder.btnFavorite.setImageResource(R.drawable.ic_heart_filled)
                }
            }
        }
    }

    override fun getItemCount(): Int = mealList.size

    // 🔍 Smart Search Support: Replace the list and refresh the UI
    fun updateList(newList: List<MealPlan>) {
        mealList = newList
        notifyDataSetChanged()
    }

}

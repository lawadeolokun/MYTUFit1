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

class MealPlanAdapter(private val mealList: List<MealPlan>) :
    RecyclerView.Adapter<MealPlanAdapter.MealViewHolder>() {

    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val db = FirebaseFirestore.getInstance()

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal_plan, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]

        // Stop if no reference or user
        val uid = userId ?: return
        val docId = meal.docId.ifEmpty { return }

        // Bind text and image
        holder.tvName.text = meal.name
        holder.tvCategory.text = meal.category
        holder.tvCalories.text = "${meal.calories} kcal"
        holder.tvProtein.text = "${meal.protein}g Protein"
        holder.tvCarbs.text = "${meal.carbs}g Carbs"
        holder.tvDuration.text = meal.duration
        holder.tvIngredients.text = meal.ingredients.joinToString("\n")
        holder.tvDirections.text = meal.directions
        Glide.with(holder.itemView.context).load(meal.imageUrl).into(holder.ivMealImage)

        // Reference to user's favorites
        val favDoc = db.collection("users").document(uid)
            .collection("favorites").document(docId)


        // Load favorite icon
        favDoc.get().addOnSuccessListener {
            val isFav = it.exists()
            holder.btnFavorite.setImageResource(
                if (isFav) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            )
        }

        // Handle heart icon click
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

    override fun getItemCount() = mealList.size
}

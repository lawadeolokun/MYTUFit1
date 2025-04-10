package com.example.mytufit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DrinkPlanAdapter(private var drinkList: List<DrinkPlan>) :
    RecyclerView.Adapter<DrinkPlanAdapter.DrinkViewHolder>() {

    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val db = FirebaseFirestore.getInstance()

    // ViewHolder class holds references to views in each item
    inner class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivDrinkImage: ImageView = itemView.findViewById(R.id.ivMealImage)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal_plan, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinkList[position]

        val uid = userId ?: return
        val docId = drink.docId.ifEmpty { return }

        holder.tvName.text = drink.name
        holder.tvCategory.text = drink.category
        holder.tvCalories.text = "${drink.calories} kcal"
        holder.tvProtein.text = "${drink.protein}g Protein"
        holder.tvCarbs.text = "${drink.carbs}g Carbs"
        holder.tvDuration.text = drink.duration
        holder.tvIngredients.text = drink.ingredients.joinToString("\n")
        holder.tvDirections.text = drink.directions
        Glide.with(holder.itemView.context).load(drink.imageUrl).into(holder.ivDrinkImage)

        val favDoc = db.collection("users").document(uid)
            .collection("favorites").document(docId)

        favDoc.get().addOnSuccessListener {
            val isFav = it.exists()
            holder.btnFavorite.setImageResource(
                if (isFav) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            )
        }

        holder.btnFavorite.setOnClickListener {
            favDoc.get().addOnSuccessListener { doc ->
                if (doc.exists()) {
                    favDoc.delete()
                    holder.btnFavorite.setImageResource(R.drawable.ic_heart_outline)
                } else {
                    favDoc.set(drink)
                    holder.btnFavorite.setImageResource(R.drawable.ic_heart_filled)
                }
            }
        }
    }

    override fun getItemCount() = drinkList.size

    // Called by fragment to refresh list during search
    fun updateList(newList: List<DrinkPlan>) {
        drinkList = newList
        notifyDataSetChanged()
    }
}

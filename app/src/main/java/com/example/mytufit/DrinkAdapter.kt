package com.example.mytufit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DrinkAdapter(private val drinks: List<Drink>) :
    RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDrinkName: TextView = itemView.findViewById(R.id.tvDrinkName)
        val tvDrinkType: TextView = itemView.findViewById(R.id.tvDrinkType)
        val tvIngredients: TextView = itemView.findViewById(R.id.tvIngredients)
        val tvDirections: TextView = itemView.findViewById(R.id.tvDirections)
        val ivDrinkImage: ImageView = itemView.findViewById(R.id.ivDrinkImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_drink, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinks[position]
        holder.tvDrinkName.text = drink.name
        holder.tvDrinkType.text = drink.type
        holder.tvIngredients.text = drink.ingredients.joinToString("\n")
        holder.tvDirections.text = drink.directions

        Glide.with(holder.itemView.context)
            .load(drink.imageUrl)
            .into(holder.ivDrinkImage)
    }

    override fun getItemCount() = drinks.size
}

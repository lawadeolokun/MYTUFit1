package com.example.mytufit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(private val workouts: List<Workout>) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWorkoutName: TextView = itemView.findViewById(R.id.tvWorkoutName)
        val tvLevel: TextView = itemView.findViewById(R.id.tvWorkoutLevel)
        val tvDescription: TextView = itemView.findViewById(R.id.tvWorkoutDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.tvWorkoutName.text = workout.name
        holder.tvLevel.text = workout.level
        holder.tvDescription.text = workout.description
    }

    override fun getItemCount() = workouts.size
}

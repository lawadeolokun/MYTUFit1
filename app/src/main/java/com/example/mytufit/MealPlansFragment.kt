package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MealPlansFragment : Fragment() {

    private lateinit var rvMealPlans: RecyclerView
    private lateinit var adapter: MealPlanAdapter
    private val mealList = mutableListOf<MealPlan>()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_meal_plans, container, false)

        // Setup RecyclerView
        rvMealPlans = view.findViewById(R.id.rvMealPlans)
        rvMealPlans.layoutManager = LinearLayoutManager(requireContext())
        adapter = MealPlanAdapter(mealList)
        rvMealPlans.adapter = adapter

        fetchMeals()

        return view
    }
    // Load meal plans from Firestore and update RecyclerView
    private fun fetchMeals() {
        firestore.collection("mealPlans")
            .get()
            .addOnSuccessListener { snapshot ->
                mealList.clear()
                for (doc in snapshot.documents) {
                    val meal = doc.toObject(MealPlan::class.java)
                    meal?.let { mealList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load meals", Toast.LENGTH_SHORT).show()
            }
    }
}

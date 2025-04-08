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
import android.util.Log


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
        Toast.makeText(requireContext(), "fetchMeals called", Toast.LENGTH_SHORT).show()
        Log.d("MEAL_FETCH", "fetchMeals() triggered")

        firestore.collection("mealPlans")
            .get()
            .addOnSuccessListener { snapshot ->
                Log.d("MEAL_FETCH", "Fetched ${snapshot.size()} documents")

                mealList.clear()
                for (doc in snapshot.documents) {
                    try {
                        val meal = doc.toObject(MealPlan::class.java)
                        if (meal != null) {
                            mealList.add(meal)
                            Log.d("MEAL_FETCH", "Added meal: ${meal.name}")
                        } else {
                            Log.w("MEAL_FETCH", "Conversion returned null for ${doc.id}")
                        }
                    } catch (e: Exception) {
                        Log.e("MEAL_FETCH", "Failed to convert ${doc.id}: ${e.message}")
                    }
                }


                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.e("MEAL_FETCH", "Error: ${it.message}", it)
            }
    }

}

package com.example.mytufit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


class MealPlansFragment : Fragment() {

    private lateinit var rvMealPlans: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: MealPlanAdapter
    private val mealList = mutableListOf<MealPlan>()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_meal_plans, container, false)

        // Bind views
        searchView = view.findViewById(R.id.searchView)
        rvMealPlans = view.findViewById(R.id.rvMealPlans)

        // Setup RecyclerView
        rvMealPlans.layoutManager = LinearLayoutManager(requireContext())
        adapter = MealPlanAdapter(mealList)
        rvMealPlans.adapter = adapter

        // Load meals initially
        fetchMeals()

        // Listen to search input
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchMeals(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchMeals(it) }
                return true
            }
        })

        return view
    }

    // Load all meals
    private fun fetchMeals() {
        mealList.clear()
        firestore.collection("mealPlans")
            .get()
            .addOnSuccessListener { snapshot ->
                for (doc in snapshot.documents) {
                    val meal = doc.toObject(MealPlan::class.java)
                    if (meal != null) mealList.add(meal)
                }
                adapter.updateList(mealList)
            }
    }


    // Search meals by name, category, or ingredients
    private fun searchMeals(query: String) {
        Log.d("MEALS", "searchMeals called with: $query")
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            fetchMeals()
            return
        }

        val results = mutableSetOf<MealPlan>() // Avoid duplicates across multiple searches


        // Search by name (partial match)
        firestore.collection("mealPlans")
            .orderBy("name")
            .startAt(trimmed).endAt(trimmed + '\uf8ff')
            .get()
            .addOnSuccessListener { snap1 ->
                snap1.documents.mapNotNullTo(results) { it.toObject(MealPlan::class.java) }

                firestore.collection("mealPlans")
                    .whereEqualTo("category", trimmed)
                    .get()
                    .addOnSuccessListener { snap2 ->
                        snap2.documents.mapNotNullTo(results) { it.toObject(MealPlan::class.java) }

                        firestore.collection("mealPlans")
                            .whereArrayContains("ingredients", trimmed)
                            .get()
                            .addOnSuccessListener { snap3 ->
                                snap3.documents.mapNotNullTo(results) { it.toObject(MealPlan::class.java) }
                                adapter.updateList(results.toList())
                            }
                    }
            }
    }
}



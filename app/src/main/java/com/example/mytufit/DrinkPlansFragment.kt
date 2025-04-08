package com.example.mytufit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class DrinkPlansFragment : Fragment() {

    private lateinit var rvDrinkPlans: RecyclerView
    private lateinit var adapter: DrinkPlanAdapter
    private val drinkList = mutableListOf<DrinkPlan>()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_meal_plans, container, false)

        // Setup RecyclerView
        rvDrinkPlans = view.findViewById(R.id.rvMealPlans)
        rvDrinkPlans.layoutManager = LinearLayoutManager(requireContext())
        adapter = DrinkPlanAdapter(drinkList)
        rvDrinkPlans.adapter = adapter

        fetchDrinks()

        return view
    }

    // Fetch drinks from Firestore
    private fun fetchDrinks() {
        Toast.makeText(requireContext(), "Loading drinks...", Toast.LENGTH_SHORT).show()
        firestore.collection("drinkPlans")
            .get()
            .addOnSuccessListener { snapshot ->
                drinkList.clear()
                for (doc in snapshot.documents) {
                    val drink = doc.toObject(DrinkPlan::class.java)
                    if (drink != null) drinkList.add(drink)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.e("DRINK_FETCH", "Error: ${it.message}", it)
            }
    }
}

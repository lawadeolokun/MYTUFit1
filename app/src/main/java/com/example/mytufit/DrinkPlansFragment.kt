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

class DrinkPlansFragment : Fragment() {

    private lateinit var rvDrinkPlans: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: DrinkPlanAdapter
    private val drinkList = mutableListOf<DrinkPlan>()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_drink_plans, container, false)

        // Bind views
        searchView = view.findViewById(R.id.searchView)
        rvDrinkPlans = view.findViewById(R.id.rvDrinkPlans)

        // Setup RecyclerView
        rvDrinkPlans.layoutManager = LinearLayoutManager(requireContext())
        adapter = DrinkPlanAdapter(drinkList)
        rvDrinkPlans.adapter = adapter

        fetchDrinks()

        // Listen for search input
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchDrinks(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchDrinks(it) }
                return true
            }
        })

        return view
    }
    // Load all drinks initially
    private fun fetchDrinks() {
        drinkList.clear()
        firestore.collection("drinkPlans")
            .get()
            .addOnSuccessListener { snapshot ->
                for (doc in snapshot.documents) {
                    val drink = doc.toObject(DrinkPlan::class.java)?.copy(docId = doc.id)
                    if (drink != null) drinkList.add(drink)
                }
                adapter.updateList(drinkList)
            }
    }


    private fun searchDrinks(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            fetchDrinks()
            return
        }

        val results = mutableSetOf<DrinkPlan>()


        firestore.collection("drinkPlans")
            .orderBy("name")
            .startAt(trimmed).endAt(trimmed + '\uf8ff')
            .get()
            .addOnSuccessListener { snap1 ->
                snap1.documents.mapNotNullTo(results) { it.toObject(DrinkPlan::class.java) }

                firestore.collection("drinkPlans")
                    .whereEqualTo("category", trimmed)
                    .get()
                    .addOnSuccessListener { snap2 ->
                        snap2.documents.mapNotNullTo(results) { it.toObject(DrinkPlan::class.java) }

                        firestore.collection("drinkPlans")
                            .whereArrayContains("ingredients", trimmed)
                            .get()
                            .addOnSuccessListener { snap3 ->
                                snap3.documents.mapNotNullTo(results) { it.toObject(DrinkPlan::class.java) }
                                adapter.updateList(results.toList())
                            }
                    }
            }
    }
}


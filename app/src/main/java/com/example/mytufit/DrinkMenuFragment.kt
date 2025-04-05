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

class DrinkMenuFragment : Fragment() {

    private lateinit var rvDrinks: RecyclerView
    private lateinit var adapter: DrinkAdapter
    private val drinkList = mutableListOf<Drink>()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drink_menu, container, false)

        rvDrinks = view.findViewById(R.id.rvDrinkMenu)
        rvDrinks.layoutManager = LinearLayoutManager(requireContext())
        adapter = DrinkAdapter(drinkList)
        rvDrinks.adapter = adapter

        fetchDrinks()

        return view
    }

    private fun fetchDrinks() {
        firestore.collection("drinks")
            .get()
            .addOnSuccessListener { snapshot ->
                drinkList.clear()
                for (doc in snapshot.documents) {
                    val drink = doc.toObject(Drink::class.java)
                    drink?.let { drinkList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load drinks", Toast.LENGTH_SHORT).show()
            }
    }
}

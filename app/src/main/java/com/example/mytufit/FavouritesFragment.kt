package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesFragment : Fragment() {

    private lateinit var rvFavorites: RecyclerView
    private lateinit var adapter: MealPlanAdapter
    private val favoriteList = mutableListOf<MealPlan>()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        rvFavorites = view.findViewById(R.id.rvFavorites)
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        adapter = MealPlanAdapter(favoriteList)
        rvFavorites.adapter = adapter

        fetchFavorites()

        return view
    }

    // Load user's favorites from Firestore
    private fun fetchFavorites() {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).collection("favorites")
            .get()
            .addOnSuccessListener { snapshot ->
                favoriteList.clear()
                for (doc in snapshot.documents) {
                    val meal = doc.toObject(MealPlan::class.java)
                    meal?.let { favoriteList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load favorites", Toast.LENGTH_SHORT).show()
            }
    }
}



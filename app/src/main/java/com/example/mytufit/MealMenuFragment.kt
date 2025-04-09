package com.example.mytufit

import android.widget.Button
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MealMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_menu, container, false)

        // Setup toolbar back navigation
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarMealMenu)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_mealMenuFragment_to_homeFragment)
        }

        // Navigate to Meal Plans screen
        val btnMealPlans = view.findViewById<Button>(R.id.btnMealPlans)
        btnMealPlans.setOnClickListener {
            findNavController().navigate(R.id.action_mealMenuFragment_to_mealPlansFragment)
        }

        // Navigate to Drink Plans screen
        val btnDrinkPlans = view.findViewById<Button>(R.id.btnDrinkPlans)
        btnDrinkPlans.setOnClickListener {
            findNavController().navigate(R.id.action_mealMenuFragment_to_drinkPlansFragment)
        }

        //Navigate to favourite section
        val btnFavorites = view.findViewById<Button>(R.id.btnFavorites)
        btnFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_mealMenuFragment_to_favoritesFragment)
        }



        return view
    }
}
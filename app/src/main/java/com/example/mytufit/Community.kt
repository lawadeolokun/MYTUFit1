package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class CommunityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        // Initialize buttons
        val btnWeightLoss = view.findViewById<Button>(R.id.btnWeightLoss)
        val btnWeightGain = view.findViewById<Button>(R.id.btnWeightGain)
        val btnRunningGroup = view.findViewById<Button>(R.id.btnRunningGroup)
        val btnBestSnacksForProtein = view.findViewById<Button>(R.id.btnBestSnacksForProtein)
        val btnStretches = view.findViewById<Button>(R.id.btnStretches)
        val btnGeneralChat = view.findViewById<Button>(R.id.btnGeneralChat)

        // Set click listeners to navigate to respective fragments
        btnWeightLoss.setOnClickListener {
            navigateToFragment(WriteFragment.newInstance("Weight Loss"))
        }
        btnWeightGain.setOnClickListener {
            navigateToFragment(WriteFragment.newInstance("Weight Gain"))
        }
        btnRunningGroup.setOnClickListener {
            navigateToFragment(WriteFragment.newInstance("Running Group"))
        }
        btnBestSnacksForProtein.setOnClickListener {
            navigateToFragment(WriteFragment.newInstance("Best Snacks for Protein"))
        }
        btnStretches.setOnClickListener {
            navigateToFragment(WriteFragment.newInstance("Stretches"))
        }
        btnGeneralChat.setOnClickListener {
            navigateToFragment(WriteFragment.newInstance("General Chat"))
        }

        return view
    }

    // Helper function to navigate to a new fragment
    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

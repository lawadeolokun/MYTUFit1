package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the XML layout
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Bind UI elements
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val btnCommunity = view.findViewById<Button>(R.id.btnCommunity)
        val btnWorkouts = view.findViewById<Button>(R.id.btnWorkouts)
        val btnMealMenu = view.findViewById<Button>(R.id.btnMealMenu)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // fetch name database
        val userId = authcurrentUser?.uid
        if (userId  != null) {
            firebaseHelper.readUserData(userId) { user ->
                user?.let {
                    // Update greeting with user's name
                    tvGreeting.text = "Hi, ${it.name}"
                }
            }
        }


        // Navigate to CommunityFragment
        btnCommunity.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_communityFragment)
        }

        // Navigate to WorkoutsFragment
        btnWorkouts.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_workoutsFragment)
        }

        // Navigate to MealMenuFragment
        btnMealMenu.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mealMenuFragment)
        }

        // Logout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        return view
    }
}

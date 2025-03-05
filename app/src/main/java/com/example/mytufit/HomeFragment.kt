package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val welcomeTextView = view.findViewById<TextView>(R.id.tvWelcome)
        val logoutButton = view.findViewById<Button>(R.id.btnLogout)

        // Retrieve username from arguments (passed after login)
        val username = arguments?.getString("USERNAME") ?: "User"
        welcomeTextView.text = "Welcome, $username!"

        logoutButton.setOnClickListener {
            // Navigate back to Login screen
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        return view
    }
}
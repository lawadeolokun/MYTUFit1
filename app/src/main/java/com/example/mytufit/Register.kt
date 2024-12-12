package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize the DatabaseHelper
        dbHelper = DatabaseHelper(requireContext())

        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val nameInput = view.findViewById<EditText>(R.id.etName)
        val emailInput = view.findViewById<EditText>(R.id.etEmail)
        val passwordInput = view.findViewById<EditText>(R.id.etPassword)
        val confirmPasswordInput = view.findViewById<EditText>(R.id.etConfirmPassword)
        val registerButton = view.findViewById<Button>(R.id.btnRegister)

        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Attempt to register the user in the database
                val isRegistered = dbHelper.registerUser(name, email, password)
                if (isRegistered) {
                    Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Registration Failed. Email might already be registered.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }
}

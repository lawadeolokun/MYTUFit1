package com.example.mytufit

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = DatabaseHelper(requireContext())

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val emailInput = view.findViewById<EditText>(R.id.etEmail)
        val passwordInput = view.findViewById<EditText>(R.id.etPassword)
        val showPasswordToggle = view.findViewById<ImageView>(R.id.ivShowPassword)
        val loginButton = view.findViewById<Button>(R.id.btnLogin)
        val forgotPassword = view.findViewById<TextView>(R.id.tvForgotPassword)

        // Toggle Password Visibility
        showPasswordToggle.setOnClickListener {
            if (passwordInput.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordToggle.setImageResource(R.drawable.showpassword)
            } else {
                passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordToggle.setImageResource(R.drawable.hidepassword)
            }
            passwordInput.setSelection(passwordInput.text.length) // Maintain cursor position
        }

        // Login Button Logic
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                val isLoggedIn = dbHelper.loginUser(email, password)
                if (isLoggedIn) {
                    Toast.makeText(requireContext(), "You have logged in successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Email or Password is incorrect. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Forgot Password Logic
        forgotPassword.setOnClickListener {
            Toast.makeText(requireContext(), "Password recovery is not implemented yet.", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}

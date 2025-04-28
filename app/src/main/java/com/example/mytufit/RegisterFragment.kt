package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

// RegisterFragment: allows users to create an account
class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val firebaseHelper = FirebaseHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the registration layout
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        auth = FirebaseAuth.getInstance()

        // Bind UI elements
        val etName = view.findViewById<EditText>(R.id.etName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val tvGotoLogin = view.findViewById<TextView>(R.id.tvGotoLogin)

        // Register button click logic
        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            } else if(!isValidPassword(password)){
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Weak Password")
                    .setMessage(
                        "Password must:\n" +
                                "- Be at least 8 characters\n" +
                                "- Include uppercase and lowercase letters\n" +
                                "- Include a number\n" +
                                "- Include a special character (!@#\$%^&*)"
                    )
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val userId = auth.currentUser?.uid
                            userId?.let {
                                firebaseHelper.writeNewUser(userId, name, email)
                            }
                            Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show()
                            // Navigate back to LoginFragment after successful registration
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            Toast.makeText(requireContext(), "Registration Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        // Navigate to LoginFragment if the user already has an account
        tvGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }

    private fun isValidPassword(password: String): Boolean {
        // Check basic rules
        if (password.length < 8) return false

        // Check character rules
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }

        return hasUppercase && hasLowercase && hasDigit && hasSpecial
    }

}

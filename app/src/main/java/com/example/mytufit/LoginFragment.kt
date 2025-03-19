package com.example.mytufit

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the XML layout
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        // Bind UI
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val ivShowPassword = view.findViewById<ImageView>(R.id.ivShowPassword)
        val tvGotoRegister = view.findViewById<TextView>(R.id.tvGotoRegister)



        // Toggle password visibility
        ivShowPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivShowPassword.setImageResource(R.drawable.hidepassword)
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivShowPassword.setImageResource(R.drawable.showpassword)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Login
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
                            // Navigate to HomeFragment
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            Toast.makeText(requireContext(), "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        // Navigate to the Register screen if user doesn't have an account
        tvGotoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }
}

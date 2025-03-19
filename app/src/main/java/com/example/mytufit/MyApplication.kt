package com.example.mytufit

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase when the application starts
        FirebaseApp.initializeApp(this)
    }
}
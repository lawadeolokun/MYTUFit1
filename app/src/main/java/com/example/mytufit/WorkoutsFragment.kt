package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.widget.Button


class WorkoutsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workouts, container, false)

        // Set up toolbar with up arrow
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarWorkouts)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_workoutsFragment_to_homeFragment)
        }
        val btnBackWorkouts = view.findViewById<Button>(R.id.btnBackWorkouts)
        btnBackWorkouts.setOnClickListener {
            findNavController().navigate(R.id.action_workoutsFragment_to_backWorkoutsFragment)
        }
        val btnArmWorkouts = view.findViewById<Button>(R.id.btnArmWorkouts)
        btnArmWorkouts.setOnClickListener {
            findNavController().navigate(R.id.action_workoutsFragment_to_armsWorkoutsFragment)
        }

        val btnLegWorkouts = view.findViewById<Button>(R.id.btnLegWorkouts)
        btnLegWorkouts.setOnClickListener {
            findNavController().navigate(R.id.action_workoutsFragment_to_legsWorkoutsFragment)
        }

        return view
    }
}
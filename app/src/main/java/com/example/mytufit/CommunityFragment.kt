package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

// CommunityFragment uses a hybrid approach it inflates an XML layout with buttons
class CommunityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the XML layout
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        // Find each button by ID
        val btnWeightLoss = view.findViewById<Button>(R.id.btnWeightLoss)
        val btnWeightGain = view.findViewById<Button>(R.id.btnWeightGain)
        val btnRunningGroup = view.findViewById<Button>(R.id.btnRunningGroup)
        val btnBestSnacksForProtein = view.findViewById<Button>(R.id.btnBestSnacksForProtein)
        val btnStretches = view.findViewById<Button>(R.id.btnStretches)
        val btnGeneralChat = view.findViewById<Button>(R.id.btnGeneralChat)

        /*
        val btnLoadTesting = view.findViewById<Button>(R.id.btnLoadTesting)

        btnLoadTesting.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_loadTestingFragment)
        }
        */

        // When a button is clicked, navigate to TopicDetailFragment with the chosen topic name.
        btnWeightLoss.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", "Weight Loss") }
            findNavController().navigate(R.id.action_communityFragment_to_topicDetailFragment, bundle)
        }
        btnWeightGain.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", "Weight Gain") }
            findNavController().navigate(R.id.action_communityFragment_to_topicDetailFragment, bundle)
        }
        btnRunningGroup.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", "Running Group") }
            findNavController().navigate(R.id.action_communityFragment_to_topicDetailFragment, bundle)
        }
        btnBestSnacksForProtein.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", "Best Snacks and Meals") }
            findNavController().navigate(R.id.action_communityFragment_to_topicDetailFragment, bundle)
        }
        btnStretches.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", "Best Stretches") }
            findNavController().navigate(R.id.action_communityFragment_to_topicDetailFragment, bundle)
        }
        btnGeneralChat.setOnClickListener {
            val bundle = Bundle().apply { putString("topicName", "General Chat") }
            findNavController().navigate(R.id.action_communityFragment_to_topicDetailFragment, bundle)
        }
        return view
    }

}

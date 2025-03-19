package com.example.mytufit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView

class WriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_write, container, false)

        // Get the topic passed from CommunityFragment
        val topic = arguments?.getString("topic") ?: "Community Chat"

        // Set the topic in the TextView
        val topicTextView = view.findViewById<TextView>(R.id.tvTopic)
        topicTextView.text = topic

        return view

    }

    companion object {
        fun newInstance(topic: String): WriteFragment {
            val fragment = WriteFragment()
            val args = Bundle()
            args.putString("topic", topic)
            fragment.arguments = args
            return fragment
        }


}

}
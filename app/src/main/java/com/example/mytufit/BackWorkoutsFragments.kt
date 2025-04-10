package com.example.mytufit

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


class BackWorkoutsFragment : Fragment() {

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_back_workouts, container, false)

        // Set up toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbarWorkouts)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_backWorkoutsFragment_to_workoutsFragment)
        }

        // Layout that holds all workout cards
        val containerLayout = view.findViewById<LinearLayout>(R.id.backWorkoutContainer)

        // Fetch workouts from Firestore
        firestore.collection("workouts")
            .whereEqualTo("category", "Back")
            .get()
            .addOnSuccessListener { snapshot ->
                for (doc in snapshot) {
                    val workout = doc.toObject(Workout::class.java)
                    val workoutView = createWorkoutView(inflater, container, workout)
                    containerLayout.addView(workoutView)
                }
            }

        return view
    }

    // Inflate each workout view and bind data
    private fun createWorkoutView(inflater: LayoutInflater, container: ViewGroup?, workout: Workout): View {
        val view = inflater.inflate(R.layout.item_workout, container, false)

        view.findViewById<TextView>(R.id.tvWorkoutName).text = workout.name
        view.findViewById<TextView>(R.id.tvWorkoutLevel).text = workout.level
        view.findViewById<TextView>(R.id.tvWorkoutRepsSets).text = "${workout.sets} sets • ${workout.reps} reps"
        view.findViewById<TextView>(R.id.tvWorkoutDescription).text = workout.description

        // Video setup
        val playerView = view.findViewById<PlayerView>(R.id.playerView)

        val player = ExoPlayer.Builder(requireContext()).build()
        playerView.player = player

        val mediaItem = MediaItem.fromUri(workout.videoUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = false // Only play on click if you want

// Optional: release player when view is detached (avoid leaks)
        playerView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {}
            override fun onViewDetachedFromWindow(v: View) {
                player.release()
            }
        })



        return view


    }
}

package com.example.mytufit

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.common.MediaItem

class ArmsWorkoutsFragment : Fragment() {

    private val firestore = FirebaseFirestore.getInstance()
    private val playerList = mutableListOf<ExoPlayer>() // Track all ExoPlayers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_arms_workouts, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbarArmsWorkouts)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_backWorkoutsFragment_to_workoutsFragment)
        }

        val containerLayout = view.findViewById<LinearLayout>(R.id.armsWorkoutContainer)

        firestore.collection("workouts")
            .whereEqualTo("category", "Arms")
            .get()
            .addOnSuccessListener { snapshot ->
                Toast.makeText(requireContext(), "Loaded ${snapshot.size()} workouts", Toast.LENGTH_SHORT).show()
                for (doc in snapshot) {
                    val workout = doc.toObject(Workout::class.java)
                    val workoutView = createWorkoutView(inflater, container, workout)
                    containerLayout.addView(workoutView)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        return view
    }

    private fun createWorkoutView(inflater: LayoutInflater, container: ViewGroup?, workout: Workout): View {
        val view = inflater.inflate(R.layout.item_workout, container, false)

        view.findViewById<TextView>(R.id.tvWorkoutName).text = workout.name
        view.findViewById<TextView>(R.id.tvWorkoutLevel).text = workout.level
        view.findViewById<TextView>(R.id.tvWorkoutRepsSets).text = "${workout.sets} sets • ${workout.reps} reps"
        view.findViewById<TextView>(R.id.tvWorkoutDescription).text = workout.description

        val playerView = view.findViewById<PlayerView>(R.id.playerView)
        val exoPlayer = ExoPlayer.Builder(requireContext()).build()

        val mediaItem = MediaItem.fromUri(Uri.parse(workout.videoUrl))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = false
        exoPlayer.volume = 1f

        playerView.player = exoPlayer

        playerView.useController = false // hide the controls

        playerView.setOnClickListener {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
        }

        playerList.add(exoPlayer) // Add to list for cleanup

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release all ExoPlayers
        for (player in playerList) {
            player.release()
        }
        playerList.clear()
    }
}

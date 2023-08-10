package com.android.example.myapplication

import Player
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.example.myapplication.ScoreNavFragment.Companion.newInstance
import com.android.example.myapplication.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var player: Player
    private var activityName: String = "null"
    private var booleanLevelUp: Boolean = false
    private var points: Int = 0
    private var totalScore: Int = 0
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        points = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)
        activityName = intent.getStringExtra("FROM_ACTIVITY_NAME").toString()
        booleanLevelUp = intent.getBooleanExtra("LEVEL_UP_BOOLEAN", false)

        if (booleanLevelUp) {
            val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
            player = Player(prefs)

            val itemFragment = ItemFragment.newInstance(player.level)
            val scoreNavFragment = newInstance(points, activityName,true)

            showApplause(itemFragment, scoreNavFragment)
        } else {
            val scoreNavFragment = newInstance(points, activityName,false)
            supportFragmentManager.beginTransaction()
                .add(R.id.container, scoreNavFragment, "scoreNav")
                .commit()
        }

    }

    @SuppressLint("DiscouragedApi")
    fun showApplause(itemFragment: ItemFragment, scoreNavFragment: ScoreNavFragment) {
        val treasure: LottieAnimationView = binding.lottieTreasure
        treasure.setAnimation(R.raw.congulatulation)

        val soundResource = "level_up"
        val mediaResource = resources.getIdentifier(soundResource, "raw", packageName)
        val mediaPlayer = MediaPlayer.create(this, mediaResource)
        mediaPlayer.setOnErrorListener { _, what, extra ->
            // Log or display the error details
            Log.e("MediaPlayer", "Error occurred: what=$what, extra=$extra")
            true // Return true to indicate that the error has been handled
        }

        mediaPlayer.start()

        treasure.playAnimation()

        treasure.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                treasure.animate()
                    .alpha(0f)
                    .setDuration(2000)
                    .withEndAction {
                        treasure.visibility = View.GONE

                    }
            }
        })

        treasure.postDelayed({
            treasure.cancelAnimation()
            var commit = supportFragmentManager.beginTransaction()
                .add(R.id.container, scoreNavFragment, "scoreNav")
                .add(R.id.container, itemFragment)
                .commit()
        }, 3000)
    }
}

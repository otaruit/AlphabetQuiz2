package com.android.example.myapplication

import Player
import SharedViewModel
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
import java.util.*


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var player: Player
    private var activityName: String = "null"
    private var booleanLevelUp: Boolean = false
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        player = Player(prefs, this)

        points = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)
        activityName = intent.getStringExtra("FROM_ACTIVITY_NAME").toString()
        booleanLevelUp = intent.getBooleanExtra("LEVEL_UP_BOOLEAN", false)

        if (booleanLevelUp) {
            val itemFragment = ItemFragment.newInstance(player.level)
            val scoreNavFragment = newInstance(points, activityName, true)
            showApplause(itemFragment, scoreNavFragment)
        } else {
            val scoreNavFragment = newInstance(points, activityName, false)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, scoreNavFragment, "scoreNav")
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
            Log.e("MediaPlayer", "Error occurred: what=$what, extra=$extra")
            true         }
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
            supportFragmentManager.beginTransaction()
                .add(R.id.container, scoreNavFragment, "scoreNav")
                .add(R.id.container, itemFragment, "item")
                .commit()
        }, 3000)
    }
}

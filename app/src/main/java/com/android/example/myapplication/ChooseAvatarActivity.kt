package com.android.example.myapplication

import Player
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.android.example.myapplication.databinding.ActivityChooseAvatarBinding

class ChooseAvatarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseAvatarBinding
    private lateinit var player:Player
    var lastSelectedButton: ImageView? = null
    private var lastSelectedButtonAnimator: ObjectAnimator? = null
    var avatarInt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChooseAvatarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun savePlayer(prefs:SharedPreferences) {
        // 正解数を取得
        var name = intent.getStringExtra("INPUTTED_NAME")
        if (name == "") name = "ななしのごんべ"
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("userName", name)
        editor.putInt("totalScore", 0)
        editor.putInt("avatarNum", avatarInt)
        editor.apply()
    }

    fun getAvatarId(view: View) {
        lastSelectedButton?.let { resetButtonBackgrounds(it) }
        lastSelectedButtonAnimator?.cancel()

        when (view.id) {
            R.id.avatar1 -> {
                avatarInt = 0
            }
            R.id.avatar2 -> {
                avatarInt = 1
            }
            R.id.avatar3 -> {
                avatarInt = 2
            }
        }

        val animator = ObjectAnimator.ofArgb(
            view,
            "backgroundColor",
            /* Start color(original color) */ resources.getColor(R.color.light_blue_600),
            /* End color (desired color) */ resources.getColor(R.color.yellow)
        )
        animator.duration = 1000 // Animation duration in milliseconds (adjust as needed)
        animator.start()

        lastSelectedButtonAnimator = animator
        lastSelectedButton = view as ImageView
    }

    private fun resetButtonBackgrounds(button: ImageView) {
        val originalColor = resources.getColor(android.R.color.transparent)
        button.setBackgroundColor(originalColor)
    }


    // スタートボタン
    fun goToNextActivity(view: View) {
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        savePlayer(prefs)

        //次のActivityへ
        startActivity(Intent(this@ChooseAvatarActivity, AlphabetQuizActivity::class.java))
    }
}


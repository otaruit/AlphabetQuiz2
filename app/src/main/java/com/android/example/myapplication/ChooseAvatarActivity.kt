package com.android.example.myapplication

import Player
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager2.widget.ViewPager2
import com.android.example.myapplication.databinding.ActivityChooseAvatarBinding
import com.android.example.myapplication.databinding.ActivityInputNameBinding
import com.android.example.myapplication.databinding.ActivityNewgameBinding
import kotlinx.coroutines.NonCancellable.start

class ChooseAvatarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseAvatarBinding
    var lastSelectedButton: ImageView? = null
    private var lastSelectedButtonAnimator: ObjectAnimator? = null
    var avatarInt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChooseAvatarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }

    private fun savePlayer() {
        // 正解数を取得
        var name = intent.getStringExtra("INPUTTED_NAME")
        if (name == "") {
            name = "ななしのごんべ"
        }

        val systemFile = applicationContext as SystemFile
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        systemFile.player = Player(prefs)
        if (name != null) {
            systemFile.player!!.name = name
        }
        systemFile.player!!.avatar = avatarInt
        systemFile.player!!.score = 0
        systemFile.player!!.savePlayerInformation()

    }

    fun getAvatarId(view: View) {
        lastSelectedButton?.let { resetButtonBackgrounds(it) }

        lastSelectedButtonAnimator?.cancel()

        val buttonId = view.id

        when (buttonId) {
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
            /* Start color(original color) */ resources.getColor(R.color.black),
            /* End color (desired color) */ resources.getColor(R.color.red)
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
        savePlayer()

        //次のActivityへ
        startActivity(Intent(this@ChooseAvatarActivity, MainActivity::class.java))
    }
}


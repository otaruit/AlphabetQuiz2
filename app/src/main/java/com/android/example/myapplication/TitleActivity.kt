package com.android.example.myapplication

import Player
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {

    private lateinit var systemFile: SystemFile
    private lateinit var binding: ActivityTitleBinding

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTitleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getPlayer()

        // 「いちもじ」ボタン
        binding.buttonOneAlphabet.setOnClickListener {
            startActivity(Intent(this@TitleActivity, MainActivity::class.java))
        }

        // 「たんご」ボタン
        binding.buttonWord.setOnClickListener {
            startActivity(Intent(this@TitleActivity, WordTestActivity::class.java))
        }

        // 「あたらしくはじめる」ボタン
        binding.buttonNewGame.setOnClickListener {
            startActivity(Intent(this@TitleActivity, InputNameActivity::class.java))
        }

    }

    @SuppressLint("DiscouragedApi")
    private fun getPlayer() {      // プレイヤー表示
        systemFile = applicationContext as SystemFile
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        systemFile.player = Player(prefs)

        binding.yourCharacter.setImageResource(systemFile.player!!.imgResources)
        characterMove()
    }

    // キャラクターを動かす
    private fun characterMove() {
        val character: ImageView = binding.yourCharacter
        val animation = AnimationUtils.loadAnimation(this, R.anim.character_move_anim)
        character.startAnimation(animation)
//        val translationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0F)
//        val translationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y,100F )
//        val animator = ObjectAnimator.ofPropertyValuesHolder(character, translationX, translationY).apply {
//            duration = 500
//            interpolator = LinearInterpolator() // 急速に開始し、その後減速しながら変化させる
//        }
//        animator.repeatCount=-1
//        animator.start()

    }


}



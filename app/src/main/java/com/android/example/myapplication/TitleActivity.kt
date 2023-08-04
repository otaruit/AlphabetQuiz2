package com.android.example.myapplication

import Player
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityTitleBinding
    private lateinit var player:Player

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTitleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        player = Player(prefs)
        getPlayer()

        // 「いちもじ」ボタン
        binding.buttonOneAlphabet.setOnClickListener {
            startActivity(Intent(this@TitleActivity, AlphabetQuizActivity::class.java))
        }

        // 「たんご」ボタン
        binding.buttonWord.setOnClickListener {
            startActivity(Intent(this@TitleActivity, WordQuizActivity::class.java))
        }

        // 「あたらしくはじめる」ボタン
        binding.buttonNewGame.setOnClickListener {
            startActivity(Intent(this@TitleActivity, InputNameActivity::class.java))
        }

    }

    @SuppressLint("DiscouragedApi", "SetTextI18n")
    private fun getPlayer() {
        var level = player.level
        binding.labelLevel.text = "Lv.$level"
        binding.labelName.text = "${player.name}ちゃん"
        binding.yourCharacter.setImageResource(player.imgResources)
        characterMove()
        cloudMove()

        // プログレスバー設定
        var bar: ProgressBar = findViewById(R.id.progressBar1)
        bar.max = player.levelThresholds[level-1]
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

    private fun cloudMove() {
        val cloud: ImageView = binding.cloud
        val animation = AnimationUtils.loadAnimation(this, R.anim.cloud_move_anim)
        cloud.startAnimation(animation)

    }

}



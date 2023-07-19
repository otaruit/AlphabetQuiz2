package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {

    private lateinit var player: Player
    private lateinit var binding: ActivityTitleBinding

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTitleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // プレイヤー表示
        val systemFile = applicationContext as SystemFile
        systemFile.player = Player()

        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        val name = prefs.getString("userName","ななしのごんべ") + "ちゃん"
        val characterNum = prefs.getInt("characterNum",0)
        val score = prefs.getInt("totalScore",0)

        player = Player(name,0)
        val resourceId = resources.getIdentifier(player.avator.toString(), "drawable", packageName)
        binding.yourCharacter.setImageResource(resourceId)

        characterMove()


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
            startActivity(Intent(this@TitleActivity, NewgameActivity::class.java))
        }


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



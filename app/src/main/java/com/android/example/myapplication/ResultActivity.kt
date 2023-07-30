package com.android.example.myapplication

import Player
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityResultBinding
import kotlin.properties.Delegates


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var systemFile: SystemFile
    private var totalScore by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 正解数を取得
        val score = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)

        displayScore(score)
        displayTotalScore(score)
        getPlayer()

        // 「もう一度」ボタン
        binding.tryAgainBtn.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
        }

        // 「タイトルへもどる」ボタン
        binding.backToTitle.setOnClickListener {
            startActivity(Intent(this@ResultActivity, TitleActivity::class.java))
        }

    }


    // トータルスコア表示
    private fun displayTotalScore(score: Int) {

        systemFile = applicationContext as SystemFile
        totalScore = systemFile.player?.score!!
        binding.totalScore.text = "ぜんぶで $totalScore てん かくとく！"

        // プログレスバー設定
        var bar: ProgressBar = findViewById(R.id.progressBar1)
        bar.max = 50

        val anim = ProgressBarAnimation(bar, (totalScore - score).toFloat(), totalScore.toFloat())
        anim.duration = 1000
        bar.startAnimation(anim)


    }


    // 結果を表示
    private fun displayScore(score: Int) {

        //アニメのviewを取得
//        var lottie = binding.lottieResultAnime

//        when (score) {
//            0 -> lottie.setAnimation(R.raw.alien_crying)
//            1 -> lottie.setAnimation(R.raw.alien_rain)
//            2 -> lottie.setAnimation(R.raw.alien_reading)
//            3 -> lottie.setAnimation(R.raw.alien_star_eye)
//            4 -> lottie.setAnimation(R.raw.alien_going_space)
//            else -> lottie.setAnimation(R.raw.alien_love)
//        }

        // スコアをセット
        binding.resultLabel.text = getString(R.string.result_score, score)

        //アニメーション表示
        binding.resultLabel.animate().translationY(-500f).setDuration(1000).startDelay = 0
    }

    @SuppressLint("DiscouragedApi")
    private fun getPlayer() {      // プレイヤー表示
        systemFile = applicationContext as SystemFile
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        systemFile.player = Player(prefs)

        val setName = systemFile.player?.name + "ちゃん"
        binding.labelName.text = setName

        binding.yourAvatar.setImageResource(systemFile.player!!.imgResources)
        characterMove()
    }

    // キャラクターを動かす
    private fun characterMove() {
        val character: ImageView = binding.yourAvatar
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
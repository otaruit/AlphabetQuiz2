package com.android.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

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
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        val totalScore = prefs.getInt("totalScore", 0)
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
}
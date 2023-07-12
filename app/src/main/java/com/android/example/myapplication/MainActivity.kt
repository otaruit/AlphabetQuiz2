package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.airbnb.lottie.LottieAnimationView
import com.android.example.myapplication.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Character.isUpperCase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val maxQuizCount = 5


    //クイズデータ
    private var quizData = mutableListOf<MutableList<String>>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewActivity()

        // なまえテキストに表示する
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        val name = prefs.getString("userName", "ななしのごんべ")
        binding.labelName.text = "$name ちゃん"

        // quiz_data.txtからクイズデータ読み取り
        readFile(getString(R.string.textFileName))
        quizData.shuffle()

        showNextQuiz()
    }

    // レイアウトを再描画
    private fun viewActivity() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    // テキストファイルからクイズを取得
    private fun readFile(file: String) {
        var `is`: InputStream? = null
        var br: BufferedReader? = null

        try {
            try {
                // assetsフォルダ内の sample.txt をオープンする
                `is` = this.assets.open(file)
                br = BufferedReader(InputStreamReader(`is`))
                var listPortion: MutableList<String>

                // １行ずつ読み込み、改行を付加する
                var str: String
                while (br.readLine().also { str = it } != null) {
                    listPortion = listOf(*str.split(",").toTypedArray()).toMutableList()
                    quizData.add(listPortion)
                }

            } finally {
                `is`?.close()
                br?.close()
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    // クイズを出題する
    @SuppressLint("SetTextI18n")
    private fun showNextQuiz() {
        // クイズを１問取り出す
        val quiz: MutableList<String> = quizData[0]

        // ラベル更新
        updateCountLabel(quiz)

        // ボタンを有効化
        btnNotEnabled(true)

        // 出題したクイズを削除する
        quizData.removeAt(0)
    }

    // 音声再生
    @SuppressLint("DiscouragedApi")
    fun soundPlay(view: View) {
        var alphabetNum = 0

        val uppercaseAlphabet = rightAnswer!!.toCharArray()[0].uppercaseChar()
        if (uppercaseAlphabet in 'A'..'Z') {
            alphabetNum = uppercaseAlphabet - 'A' + 1
        }

        // ボタンを押すと音声再生(あみたろの声素材工房(https://amitaro.net/)の音声を使用しました)

        val alphabetSoundResource = "R.raw.alphabet$alphabetNum"
        val uri = alphabetSoundResource.toUri()

        val mediaResource = resources.getIdentifier("alphabet$alphabetNum", "raw", packageName)
        val mediaPlayer = MediaPlayer.create(applicationContext, mediaResource)
        mediaPlayer.setVolume(1.0f, 1.0f)

        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
        } else {
            mediaPlayer.start()
        }

//        super.onDestroy()
//        mediaPlayer?.release()
    }

    // ラベルの更新
    private fun updateCountLabel(quiz: MutableList<String>) {


        binding.quizNum.text = quizCount.toString()
        binding.correctAnswerNum.text = "せいかい　${rightAnswerCount}もん"
        binding.wrongAnswerNum.text = "はずれ　${(quizCount - rightAnswerCount - 1)}もん"

        //おおもじかこもじか判断
        if (isUpperCase(quiz[0].first())) {
            binding.textQuestion.text = "は こもじ でどれ？"
        } else {
            binding.textQuestion.text = "は おおもじ でどれ？"
        }

        // 問題をセット
        binding.quizText.text = quiz[0]

        // 正解をセット
        rightAnswer = quiz[1]

        // 正解を削除
        quiz.removeAt(0)

        // 正解と選択肢３つをシャッフル
        quiz.shuffle()

        // 選択肢をセット
        binding.answer1.text = quiz[0]
        binding.answer2.text = quiz[1]
        binding.answer3.text = quiz[2]
        binding.answer4.text = quiz[3]


    }

    // 解答ボタンが押されたら呼ばれる
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAnswer(view: View) {

        btnNotEnabled(false)

        val judgeAnimation: LottieAnimationView = binding.lottieJudge
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        // 「つぎへ」ボタン表示
        val nextBtn: Button = findViewById(R.id.next_btn)
        nextBtn.visibility = View.VISIBLE


        var soundResource :String? = null


        if (btnText == rightAnswer) {
            judgeAnimation.setAnimation(R.raw.heart1)
            soundResource = "correct"
            //正解の選択肢のボタンの背景色を青に
            answerBtn.setBackgroundResource(R.drawable.correct_btn_color)

            rightAnswerCount++

        } else {
            judgeAnimation.setAnimation(R.raw.alien_crying)
             soundResource = "incorrect"
            //選択したの選択肢のボタンの背景色を赤に
            answerBtn.setBackgroundColor(R.drawable.incorrect_btn_color)

            //正解の選択肢のボタンの背景色を青に
        }

        val mediaResource = resources.getIdentifier(soundResource, "raw", packageName)
        val mediaPlayer = MediaPlayer.create(applicationContext, mediaResource)
        mediaPlayer.start()

        judgeAnimation.visibility = View.VISIBLE
        judgeAnimation.playAnimation()

    }


    private fun btnNotEnabled(boolean: Boolean) {
        if (boolean) {
            binding.answer1.isEnabled = true
            binding.answer2.isEnabled = true
            binding.answer3.isEnabled = true
            binding.answer4.isEnabled = true
        } else {
            binding.answer1.isEnabled = false
            binding.answer2.isEnabled = false
            binding.answer3.isEnabled = false
            binding.answer4.isEnabled = false
        }
    }

    // 出題数をチェックする
    fun checkQuizCount(view: View) {
        if (quizCount == maxQuizCount) {

            // SharedPreferencesにスコアを保存
            addScore()

            // 結果画面を表示
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        } else {

            quizCount++

            viewActivity()

            showNextQuiz()
        }
    }

    // totalScore保存
    private fun addScore() {
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        val totalScore = prefs.getInt("totalScore", 0) + rightAnswerCount
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt("totalScore", totalScore)
        editor.apply()
    }

}



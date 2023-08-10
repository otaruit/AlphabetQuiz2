package com.android.example.myapplication

import Player
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.example.myapplication.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Character.isUpperCase


class AlphabetQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var player: Player
    private var level: Int = 0


    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val maxQuizCount = 5
    private var correctAnswerIndex: Int = 1 // 正解の選択肢のインデックス（0から始まる）

    //クイズデータ
    private var quizData = mutableListOf<MutableList<String>>()

    private lateinit var option1Button: Button
    private lateinit var option2Button: Button
    private lateinit var option3Button: Button
    private lateinit var option4Button: Button

    @SuppressLint("SetTextI18n", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        player = Player(prefs)
        level = player.level
        println("れべる${player.level}")

        viewActivity()

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

        option1Button = binding.answer1
        option2Button = binding.answer2
        option3Button = binding.answer3
        option4Button = binding.answer4
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
        // プレイヤー表示
        val setName = "${player.name}ちゃん"
        binding.labelName.text = setName

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

        // 問題を削除
        quiz.removeAt(0)

        //正解の選択肢をセット
        val correctSelection = quiz[0]

        // 正解と選択肢３つをシャッフル
        quiz.shuffle()

        // 正解の選択肢のインデックスを更新
        correctAnswerIndex = quiz.indexOf(correctSelection)

        // 選択肢をセット
        option1Button?.text = quiz[0]
        option2Button?.text = quiz[1]
        option3Button?.text = quiz[2]
        option4Button?.text = quiz[3]

    }

    // 解答ボタンが押されたら呼ばれる
    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAnswer(view: View) {
        // 黒いオーバーレイエフェクトの色
        val overlayColor = getColor(android.R.color.black)
        // カスタムエフェクトを作成
        val blackOverlay = ColorDrawable(overlayColor)
        blackOverlay.alpha = 100 // 透明度を設定（0〜255の範囲で設定）
        binding.quizContainer.foreground = blackOverlay

        btnNotEnabled(false)

        val judgeAnimation: LottieAnimationView = binding.lottieJudge
        val selectBtn: Button = findViewById(view.id)
        var soundResource: String? = null

        val selectedIndex = when (view) {
            //選択肢
            option1Button -> 0
            option2Button -> 1
            option3Button -> 2
            option4Button -> 3
            else -> -1
        }

        if (selectedIndex == correctAnswerIndex) {
            rightAnswerCount++

            judgeAnimation.setAnimation(R.raw.correct_answer)
            judgeAnimation.layoutParams.width = 700 // 幅を指定
            judgeAnimation.layoutParams.height = 700 // 高さを指定
            judgeAnimation.requestLayout() // レイアウトの再計算を要求

            soundResource = "correct"
            selectBtn.setBackgroundResource(R.drawable.correct_btn_color)
        } else {
            // 不正解の場合、正解のボタンを緑色に変える
            val correctButton = when (correctAnswerIndex) {
                0 -> binding.answer1
                1 -> binding.answer2
                2 -> binding.answer3
                3 -> binding.answer4
                else -> binding.answer1
            }
            correctButton.setBackgroundResource(R.drawable.correct_btn_color)
            selectBtn.setBackgroundResource(R.drawable.incorrect_btn_color)

            judgeAnimation.setAnimation(R.raw.incorrect_animation)
            soundResource = "incorrect"
        }

        val mediaResource = resources.getIdentifier(soundResource, "raw", packageName)
        val mediaPlayer = MediaPlayer.create(applicationContext, mediaResource)
        mediaPlayer.start()

        judgeAnimation.playAnimation()

        judgeAnimation.postDelayed({
            judgeAnimation.cancelAnimation()
            judgeAnimation.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    judgeAnimation.visibility = View.GONE
                }
        }, 1500)

        val nextBtn = binding.nextBtn
        nextBtn.setAnimation(R.raw.next)
        nextBtn.setOnClickListener {
            nextBtn.playAnimation()
            checkQuizCount(view)
        }

        // 「つぎへ」ボタン表示
        val afterAnsweredView: View = findViewById(R.id.after_answered)
        afterAnsweredView.visibility = View.VISIBLE


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

            // スコアを保存
            player.savePlayerScore(rightAnswerCount)

            // 結果画面を表示
            val intent = Intent(this@AlphabetQuizActivity, ResultActivity::class.java)
            intent.putExtra("FROM_ACTIVITY_NAME", "one_alphabet")
            intent.putExtra("LEVEL_UP_BOOLEAN", player.checkLevelUp())
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)

        } else {
            quizCount++
            viewActivity()
            showNextQuiz()
        }
    }


}



package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // なまえテキストに表示する
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        val name = prefs.getString("userName", "ななしのごんべ")
        binding.labelName.text = "$name ちゃん"

        // quiz_data.txtからクイズデータ読み取り
        readFile(getString(R.string.textFileName))
        quizData.shuffle()


        showNextQuiz()
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

        // 出題したクイズを削除する
        quizData.removeAt(0)


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
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAnswer(view: View) {

        // どの解答ボタンが押されたか
        //リソースからボタンのリソースを取得(ここではbtn_sampleとする)
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()
//        val answerBtnId = answerBtn.id.toString()

        //リソースから作成したDrawableのリソースを取得
//        val btn_color = ResourcesCompat.getDrawable(resources, R.drawable.correct_btn_color, null)
//        //ボタンにDrawableを適用する
//        answerBtn.background = btn_color


//        answerBtn.setOnClickListener { v -> v.isSelected = !v.isSelected }


        if (btnText == rightAnswer) {
            //test
//            val judgeAnimation: LottieAnimationView = findViewById(R.id.judge_lottie)
//            judgeAnimation.setAnimation(R.raw.heart1)
//            judgeAnimation.enableMergePathsForKitKatAndAbove(true)
//            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.popup_view_anim)
//            judgeAnimation.startAnimation(animation)

            //正解の選択肢のボタンの背景色を青に
            answerBtn.setBackgroundColor(R.drawable.correct_btn_color)

            rightAnswerCount++

        } else {
            //不正解の時の挙動　不正解アニメーション表示

            //選択したの選択肢のボタンの背景色を赤に

            //正解の選択肢のボタンの背景色を青に
        }


        // Fragmentを作成します
//        val fragment = SelectAnswerFragment()
//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.main_fragment, fragment)
//        transaction.commit()

        checkQuizCount()
    }


    // 出題数をチェックする
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkQuizCount() {
        if (quizCount == maxQuizCount) {

            // SharedPreferencesにスコアを保存
            addScore()

            // 結果画面を表示
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        } else {
            quizCount++

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


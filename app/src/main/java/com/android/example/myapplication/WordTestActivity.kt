package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.text.set
import com.android.example.myapplication.databinding.ActivityWordTestBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class WordTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordTestBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val maxQuizCount = 5

    //クイズデータ
    private var quizData = mutableListOf<String>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_test)


        binding = ActivityWordTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // なまえテキストに表示する
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        val name = prefs.getString("userName", "ななしのごんべ")
        binding.labelName.text = "$name ちゃん"

        // quiz_data.txtからクイズデータ読み取り
        readFile(getString(R.string.wordTextFileName))
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
                // １行ずつ読み込み、改行を付加する
                var str: String
                while (br.readLine().also { str = it } != null) {
                    quizData += str
                    println("リストの要素　$quizData ")
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
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun showNextQuiz() {

        // ラベル更新
        updateCountLabel()

        // クイズを１問取り出す
        val lowerCaseWords: String = quizData[0]

        // ランダムに大文字か小文字をセット
        val num = Random().nextInt(2)
        if (num == 1) {
            rightAnswer = lowerCaseWords
            binding.textQuestion.text = "を こもじ にしてね！"
            binding.quizText.text = lowerCaseWords.uppercase()
        } else {
            rightAnswer = quizData[0].uppercase()
            binding.textQuestion.text = "を おおもじ にしてね！"
            binding.quizText.text = lowerCaseWords
        }

        // 出題したクイズを削除する
        quizData.removeAt(0)

    }

    // ラベルの更新
    private fun updateCountLabel() {
        binding.quizNum.text = quizCount.toString()
        binding.correctAnswerNum.text = "せいかい　$rightAnswerCount もん"
        binding.wrongAnswerNum.text = "はずれ　${(quizCount - rightAnswerCount - 1)}もん"
    }

    // 解答ボタンが押されたら呼ばれる
    @RequiresApi(Build.VERSION_CODES.O)
    fun submit(view: View) {

        val answerText = binding.textAnswer.text.toString()

        if (answerText == rightAnswer) {
            //正解の時の挙動　正解アニメーション表示

            //正解の選択肢のボタンの背景色を青に

            rightAnswerCount++
        } else {
            //不正解の時の挙動　不正解アニメーション表示

            //選択したの選択肢のボタンの背景色を赤に

            //正解の選択肢のボタンの背景色を青に
        }

        binding.textAnswer.setText("")
        checkQuizCount()

    }

    // 出題数をチェックする
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkQuizCount() {
        if (quizCount == maxQuizCount) {

            addScore()

            // 結果画面を表示
            val intent = Intent(this@WordTestActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        } else {
            quizCount++
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


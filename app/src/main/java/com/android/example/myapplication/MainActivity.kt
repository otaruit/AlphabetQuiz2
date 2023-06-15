package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private var rightAnswer: String? = null
    private var questionAlphabet: String? = null
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

        // カウントラベルの更新
        binding.quizNum.text = "だい" + quizCount.toString() + "もん"

        // なまえテキストに表示する
        val prefs = getSharedPreferences("userName", MODE_PRIVATE)
        val name = prefs.getString("userName", "ななしのごんべ")
        binding.labelName.text = "$name ちゃん"

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
                    println(quizData)
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
    private fun showNextQuiz() {

        // quiz_data.txtからクイズデータ読み取り
        readFile(getString(R.string.textFileName))
        quizData.shuffle()

        // クイズを１問取り出す
        val quiz: MutableList<String> = quizData[0]

        // 問題をセット
        binding.quizText.text = quiz[0]
        questionAlphabet = quiz[0]

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

        // 出題したクイズを削除する
        quizData.removeAt(0)

    }


    // 解答ボタンが押されたら呼ばれる
    fun checkAnswer(view: View) {

        // どの解答ボタンが押されたか
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        // ダイアログのタイトルを作成
        val alertTitle: String
        if (btnText == rightAnswer) {
            alertTitle = "せいかい!"
            rightAnswerCount++
        } else {
            alertTitle = "ざんねん、はずれ..."
        }

        // ダイアログを作成
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("こたえ : $questionAlphabet は $rightAnswer でした！")
            .setPositiveButton("つぎへ") { _, _ ->
                checkQuizCount()
            }
            .setCancelable(false)
            .show()
    }

    // 出題数をチェックする
    private fun checkQuizCount() {
        if (quizCount == maxQuizCount) {
            // 結果画面を表示
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        } else {
            quizCount++
            showNextQuiz()
        }
    }

}
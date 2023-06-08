package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){


    private lateinit var binding: ActivityMainBinding

    private var rightAnswer: String? = null
    private var questionAlphabet: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val maxQuizCount = 5

    //クイズデータ
    private val quizData = mutableListOf(
        mutableListOf("a", "A", "R", "P", "D"),
        mutableListOf("b", "B", "P", "D", "R"),
        mutableListOf("c", "C", "G", "L", "T"),
        mutableListOf("d", "D", "Q", "B", "P"),
        mutableListOf("e", "E", "F", "Y", "K"),
        mutableListOf("f", "F", "T", "Y", "P"),
        mutableListOf("g", "G", "C", "L", "Q"),
        mutableListOf("y", "Y", "H", "V", "T"),
        mutableListOf("s", "S", "J", "R", "K"),
        mutableListOf("r", "R", "B", "P", "D"),
        mutableListOf("k", "K", "F", "X", "Z"),
        mutableListOf("i", "I", "U", "L", "J"),
        mutableListOf("l", "L", "V", "I", "T"),
        mutableListOf("w", "W", "X", "V", "H"),
        mutableListOf("o", "O", "Q", "D", "P"),
        mutableListOf("j", "J", "L", "I", "T"),
        mutableListOf("t", "T", " Y", "V", "U"),
        mutableListOf("q", "Q", "O", "P", "D"),
        mutableListOf("h", "H", "K", "F", "L"),
        mutableListOf("n", "N", "M", "W", "U"),
        mutableListOf("m", "M", "W", "X", "K"),
        mutableListOf("p", "P", "B", "R", "D"),
        mutableListOf("u", "U", "V", "W", "C")
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //intentから取り出す
//        var getName = this.intent.getStringExtra("LABEL_NAME") //設定したkeyで取り出す
//        if (getName == "") {
//            getName = "ななしのごんべ"//Assign default string
//        }


        // 読み出すボタン押下時の挙動。
//        val buf: BufferedReader = readFile("contents.txt")
//        val result = buf.use { it.readText() }
//        binding.labelName.text = "$result　ちゃん"

        // なまえテキストに表示する
        val prefs = getSharedPreferences("userName", MODE_PRIVATE)
        val name = prefs.getString("userName", "ななしのごんべ")
        binding.labelName.text = "$name ちゃん"

        quizData.shuffle()
        showNextQuiz()
    }

    // 読み出し処理。
//    private fun readFile(file: String): BufferedReader {
//        val readFile = File(applicationContext.filesDir, file)
//        return readFile.bufferedReader()
//    }

    // クイズを出題する
    private fun showNextQuiz() {

        // カウントラベルの更新
        binding.quizNum.text = getString(R.string.question_num, quizCount)

        // クイズを１問取り出す
        val quiz = quizData[0]

        // 問題をセット
        binding.quizText.text = getString(R.string.question_alphabet, quiz[0])
        questionAlphabet = quiz[0]

        // 正解をセット
        rightAnswer = quiz[1]

        // 都道府県名を削除
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
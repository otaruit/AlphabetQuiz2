package com.android.example.myapplication

import Player
import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.example.myapplication.databinding.ActivityWordTestBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*


class WordTestActivity : AppCompatActivity(), TextWatcher {

    private lateinit var systemFile: SystemFile
    private lateinit var player: Player

    private lateinit var binding: ActivityWordTestBinding
    private var rightAnswer: String = ""
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val maxQuizCount = 5

    //クイズデータ
    private var quizData = mutableListOf<String>()
    private lateinit var answerEditor: EditText


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_test)

        binding = ActivityWordTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // quiz_data.txtからクイズデータ読み取り
        readFile(getString(R.string.wordTextFileName))
        quizData.shuffle()

        answerEditor = findViewById(R.id.answer_section)
        answerEditor.isFocusableInTouchMode = true
        answerEditor.requestFocus()
        answerEditor.addTextChangedListener(this)

        showNextQuiz()
    }


    // ラベルの更新
    private fun updateCountLabel() {

        binding.answerSection.setText("")

        // プレイヤー表示
        systemFile = applicationContext as SystemFile
        player = systemFile.player!!

        val setName = player.name + "ちゃん"
        binding.labelName.text = setName

//        val resourceId =
//            resources.getIdentifier(systemFile.player!!.avatarImg, "drawable", packageName)
//        binding.yourCharacter.setImageResource(resourceId)
//        characterMove()

        binding.quizNum.text = quizCount.toString()
        binding.correctAnswerNum.text = "せいかい　${rightAnswerCount}もん"
        binding.wrongAnswerNum.text = "はずれ　${(quizCount - rightAnswerCount - 1)}もん"

        // キーボード表示
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT)

        // クイズを１問取り出す
        val lowerCaseWords: String = quizData[0]

        // ランダムに大文字か小文字をセット
        val num = Random().nextInt(2)
        if (num == 1) {
            rightAnswer = lowerCaseWords.uppercase()
            binding.textQuestion.text = "を おおもじ にしてね！"
            binding.quizText.text = lowerCaseWords
        } else {
            rightAnswer = lowerCaseWords
            binding.textQuestion.text = "を こもじ にしてね！"
            binding.quizText.text = lowerCaseWords.uppercase()
        }

    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun afterTextChanged(s: Editable?) {

        if (answerEditor.text.isNotEmpty()) {
            val inputText = s.toString()
            if (inputText.length == rightAnswer.length) {
                if (inputText == rightAnswer) {
                    rightAnswerCount++
                    checkQuizCount()
                } else {
                    println("NO!!")
                }
            }

        }

    }


// 解答ボタンが押されたら呼ばれる
//   }

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
        // 出題したクイズを削除する
        quizData.removeAt(0)

    }

    // 出題数をチェックする
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkQuizCount() {
        if (quizCount == maxQuizCount) {
            // スコアを保存
            player.savePlayerScore(rightAnswerCount)
            systemFile.player = player

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

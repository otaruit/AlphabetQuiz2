package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var word: String
    private var currentIndex = 0
    private lateinit var keyboardLayout: LinearLayout


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

        keyboardLayout = findViewById(R.id.keyboardLayout)


        keyboardLayout.isFocusableInTouchMode = true
        keyboardLayout.requestFocus()

        keyboardLayout.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    val pressedKey = getPressedKey(event)
                    if (pressedKey.isLowerCase()) {
                        // 小文字のキーが押された場合の処理
                        // 例: Toast.makeText(this, "小文字：$pressedKey", Toast.LENGTH_SHORT).show()
                    } else if (pressedKey.isUpperCase()) {
                        // 大文字のキーが押された場合の処理
                        // 例: Toast.makeText(this, "大文字：$pressedKey", Toast.LENGTH_SHORT).show()
                    } else {
                        // その他の文字（数字や記号など）が押された場合の処理
                        // 例: Toast.makeText(this, "その他：$pressedKey", Toast.LENGTH_SHORT).show()
                    }
                }
                false
            }



//        keyboardLayout.setOnKeyListener { v, keyCode, event ->
//            onKey(v, keyCode, event)
//        }



        // キーボードを表示する
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


        showNextQuiz()
    }

    private fun getPressedKey(event: KeyEvent): Char {
        return event.unicodeChar.toChar()
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

        // キーボード表示
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        manager.hideSoftInputFromWindow(EditText.getWindowToken(), 0)
        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT)

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

        word = rightAnswer as String
        for (char in word) {
            val textView = TextView(this)
            textView.text = char.toString()
            textView.textSize = 30F
            keyboardLayout.addView(textView)
        }
        // 出題したクイズを削除する
        quizData.removeAt(0)

    }


    override fun dispatchKeyEvent(e: KeyEvent): Boolean {
        // キーコードを表示する

        val str = "コード「" + java.lang.String.valueOf(e.keyCode) + "」のキーが押されました"
        println("KeyCode:" + str )
        // 検索ボタンが押されたとき
        if (e.keyCode == KeyEvent.KEYCODE_SEARCH) {
            // ボタンが押し込まれたとき
            if (e.action == KeyEvent.ACTION_DOWN) {
                // 背景色を赤色にする
                println(Color.RED)
            } else if (e.action == KeyEvent.ACTION_UP) {
                // 背景色を青色にする
                println(Color.BLUE)
            }
        }
        return super.dispatchKeyEvent(e)
    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//        val keyPressed = event?.displayLabel?.toString()
//
//        if (keyCode == KeyEvent.KEYCODE_DEL) {
//            // バックスペースが押された場合
//            if (currentIndex > 0) {
//                currentIndex--
//                resetTextColor()
//            }
//        } else if (keyPressed != null && currentIndex < word.length) {
//            val currentChar = word[currentIndex].toString()
//            val textView = keyboardLayout.getChildAt(currentIndex) as TextView
//
//            if (keyPressed == currentChar) {
//                textView.setTextColor(Color.RED)
//                currentIndex++
//
//                if (currentIndex == word.length) {
//                    rightAnswerCount++
//                    checkQuizCount()
//                }
//            } else {
//                textView.setTextColor(Color.BLACK)
//            }
//        }
//
//        return true
//    }

    private fun resetTextColor() {
        for (i in 0 until keyboardLayout.childCount) {
            val textView = keyboardLayout.getChildAt(i) as TextView
            textView.setTextColor(Color.BLACK)
        }
    }

    private fun gameClear() {
        keyboardLayout.removeAllViews()
        currentIndex = 0
    }


    // ラベルの更新
    private fun updateCountLabel() {
        binding.quizNum.text = quizCount.toString()
        binding.correctAnswerNum.text = "せいかい　$rightAnswerCount もん"
        binding.wrongAnswerNum.text = "はずれ　${(quizCount - rightAnswerCount - 1)}もん"
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
            gameClear()
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


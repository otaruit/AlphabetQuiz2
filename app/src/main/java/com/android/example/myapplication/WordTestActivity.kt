package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityWordTestBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*


class WordTestActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivityWordTestBinding
    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1
    private val maxQuizCount = 5

    //クイズデータ
    private var quizData = mutableListOf<String>()

    private lateinit var word: String
    private var currentIndex = 0
    private lateinit var answerEditor: EditText


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


//        keyboardLayout = findViewById(R.id.keyboardLayout)
        answerEditor = findViewById(R.id.answer_section)

        answerEditor.isFocusableInTouchMode = true
        answerEditor.requestFocus()

        answerEditor.addTextChangedListener(this)


//        // キーボードを表示する
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


        showNextQuiz()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("Not yet implemented")
    }

    override fun afterTextChanged(s: Editable?) {

//        val answer = answerEditor.toString()
//        val currentQuestion = questions[currentQuestionIndex]
//
//        if (answer == currentQuestion) {
//            // 回答が正解の場合
//            currentQuestionIndex++
//            if (currentQuestionIndex < questions.size) {
//                displayQuestion()
//            } else {
//                // 全ての問題を終了した場合の処理
//                questionTextView.text = "全ての問題を終了しました。"
//                inputEditText.isEnabled = false
//            }
//        } else {
//            // 回答が不正解の場合
//            // ここに不正解の処理を追加する（例: エラーメッセージの表示など）
//        }
    }


//
//
//private fun getPressedKey(event: KeyEvent): Char {
//    return event.unicodeChar.toChar()
//}

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
//        answerEditor.addView(textView)
        }
        // 出題したクイズを削除する
        quizData.removeAt(0)

    }


//private fun resetTextColor() {
//    for (i in 0 until answerEditor.childCount) {
//        val textView = answerEditor.getChildAt(i) as TextView
//        textView.setTextColor(Color.BLACK)
//    }
//}
//
//private fun gameClear() {
//    answerEditor.removeAllViews()
//    currentIndex = 0
//}


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
//            gameClear()
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


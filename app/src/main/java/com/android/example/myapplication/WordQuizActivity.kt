package com.android.example.myapplication

import Player
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.example.myapplication.databinding.ActivityWordQuizBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*


class WordQuizActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivityWordQuizBinding
    private lateinit var player: Player

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
        setContentView(R.layout.activity_word_quiz)

        viewActivity()

        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        player = Player(prefs)

        // quiz_data.txtからクイズデータ読み取り
        readFile(getString(R.string.wordTextFileName))
        quizData.shuffle()

        answerEditor = findViewById(R.id.answer_section)
        answerEditor.isFocusableInTouchMode = true
        answerEditor.requestFocus()
        answerEditor.addTextChangedListener(this)

        showNextQuiz()
    }

    // レイアウトを再描画
    private fun viewActivity() {
        binding = ActivityWordQuizBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


    // ラベルの更新
    @SuppressLint("SetTextI18n")
    private fun updateCountLabel() {

        binding.answerSection.setText("")
        binding.labelName.text = "${player.name} ちゃん"

        binding.quizNum.text = quizCount.toString()
        binding.correctAnswerNum.text = "せいかい　${rightAnswerCount}もん"
        binding.wrongAnswerNum.text = "はずれ　${(quizCount - rightAnswerCount - 1)}もん"

        // キーボード表示
//        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT)

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)


        // クイズを１問取り出す
        val lowerCaseWord: String = quizData[0]
        imgChange(lowerCaseWord)

        // ランダムに大文字か小文字をセット
        val num = Random().nextInt(2)
        if (num == 1) {
            rightAnswer = lowerCaseWord.uppercase()
            binding.textQuestion.text = "を おおもじ にしてね！"
            binding.quizText.text = lowerCaseWord
        } else {
            rightAnswer = lowerCaseWord
            binding.textQuestion.text = "を こもじ にしてね！"
            binding.quizText.text = lowerCaseWord.uppercase()
        }

    }

    @SuppressLint("DiscouragedApi")
    private fun imgChange(lowerCaseWord: String) {
        val resourceId = resources.getIdentifier(lowerCaseWord, "drawable", packageName)
        binding.quizImg.setImageResource(resourceId)
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun afterTextChanged(s: Editable?) {
        val judgeAnimation: LottieAnimationView = binding.lottieJudge
        var soundResource: String? = null

        if (answerEditor.text.isNotEmpty()) {
            val inputText = s.toString()
            if (inputText.length == rightAnswer.length) {
                if (inputText == rightAnswer) {
                    rightAnswerCount++

                    judgeAnimation.setAnimation(R.raw.correct_answer)
                    judgeAnimation.layoutParams.width = 700 // 幅を指定
                    judgeAnimation.layoutParams.height = 700 // 高さを指定
                    judgeAnimation.requestLayout() // レイアウトの再計算を要求

                    soundResource = "correct"
                } else {
                    judgeAnimation.setAnimation(R.raw.incorrect_animation)
                    soundResource = "incorrect"
                }

                val mediaResource = resources.getIdentifier(soundResource, "raw", packageName)
                val mediaPlayer = MediaPlayer.create(applicationContext, mediaResource)
                mediaPlayer.start()

                judgeAnimation.postDelayed({
                    judgeAnimation.cancelAnimation()
                    judgeAnimation.animate()
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction {
                        }
                }, 1500)
                judgeAnimation.playAnimation()
                binding.afterAnswered.visibility = View.VISIBLE
            }
        }
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
        // 出題したクイズを削除する
        quizData.removeAt(0)
    }

    // 出題数をチェックする
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkQuizCount(view: View) {
        if (quizCount == maxQuizCount) {
            // スコアを保存
            player.savePlayerScore(rightAnswerCount)

            // 結果画面を表示
            val intent = Intent(this@WordQuizActivity, ResultActivity::class.java)
            intent.putExtra("FROM_ACTIVITY_NAME","one_word")
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)

        } else {
            quizCount++
            binding.afterAnswered.visibility = View.INVISIBLE
            showNextQuiz()
        }
    }
}


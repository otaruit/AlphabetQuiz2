package com.android.example.myapplication

import Player
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityTitleBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class TitleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTitleBinding

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTitleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 「いちもじ」ボタン
        binding.buttonOneAlphabet.setOnClickListener {
            startActivity(Intent(this@TitleActivity, AlphabetQuizActivity::class.java))
        }

        // 「たんご」ボタン
        binding.buttonWord.setOnClickListener {
            startActivity(Intent(this@TitleActivity, WordQuizActivity::class.java))
        }

        // 「あたらしくはじめる」ボタン
        binding.buttonNewGame.setOnClickListener {
            startActivity(Intent(this@TitleActivity, InputNameActivity::class.java))
        }
    }
}


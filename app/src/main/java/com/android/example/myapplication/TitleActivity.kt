package com.android.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_result)
        binding = ActivityTitleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 「つづきから」ボタン
        binding.buttonContinue.setOnClickListener {
            startActivity(Intent(this@TitleActivity, MainActivity::class.java))
        }

        // 「あたらしくはじめる」ボタン
        binding.buttonStart.setOnClickListener {
            startActivity(Intent(this@TitleActivity, NewgameActivity::class.java))
        }
    }
}
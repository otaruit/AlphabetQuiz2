package com.android.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityNewgameBinding
import java.io.*


class NewgameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewgameBinding

    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewgameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // スタートボタン
        binding.buttonStart.setOnClickListener {

            // なまえを取得
            var name: String = binding.textName.text.toString()
            if (name == "") {
                name = "ななしのごんべ"//Assign default string
            }

            // SharedPreferencesに名前を保存
            val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("userName", name)
            editor.putInt("totalScore", 0)
            editor.apply()


            //次のActivityへ
            startActivity(Intent(this@NewgameActivity, MainActivity::class.java))
        }
    }
}





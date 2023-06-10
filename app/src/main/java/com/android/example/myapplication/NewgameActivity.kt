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
            val prefs = getSharedPreferences("userName", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString("userName", name)
            editor.apply()


            //内部ストレージに保存
//            val fileName = "contents.txt"

            // 保存ボタン押下時の挙動。
//            saveFile(fileName, name)

            // 読み出すボタン押下時の挙動。
//            val buf: BufferedReader = readFile(fileName)
//            val result = buf.use { it.readText() }
//            binding.textWhatname.text = "$result　ちゃん"

            //次のActivityへ
            startActivity(Intent(this@NewgameActivity, MainActivity::class.java))
        }

//    // 保存処理。
//    private fun saveFile(file: String, str: String) {
//        applicationContext.openFileOutput(file, Context.MODE_PRIVATE).use {
//            it.write(str.toByteArray())
//        }
//    }
//
//    // 読み出し処理。
//    private fun readFile(file: String): BufferedReader {
//        val readFile = File(applicationContext.filesDir, file)
//        return readFile.bufferedReader()
//    }


    }
}





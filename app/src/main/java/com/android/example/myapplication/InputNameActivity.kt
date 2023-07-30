package com.android.example.myapplication

import Player
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityInputNameBinding

class InputNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputNameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun goToNextActivity(view: View) {

        // なまえを取得
        val name: String = binding.editedName.text.toString()

        //次のActivityへ
        val intent = Intent(this@InputNameActivity, ChooseAvatarActivity::class.java)
        intent.putExtra("INPUTTED_NAME", name)
        startActivity(intent)
    }
}


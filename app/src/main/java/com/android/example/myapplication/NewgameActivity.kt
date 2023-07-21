package com.android.example.myapplication

import Player
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityNewgameBinding


//import java.io.*


class NewgameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewgameBinding
//    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewgameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var animImage: ImageView = binding.animatedBackground
        val animation = animImage.drawable as AnimatedVectorDrawable
        animation.start()


        // スタートボタン
        binding.buttonStart.setOnClickListener {

            // なまえを取得
            val name: String = binding.textName.text.toString()

            val systemFile = applicationContext as SystemFile
            val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
            systemFile.player = Player(prefs)
            systemFile.player!!.name = name
            systemFile.player!!.savePlayerInformation()



////            val back:ImageView= findViewById(R.id.animatedBackground)
////            back.setImageResource(R.drawable.avd_anim_minus_to_plus)
////            val animation = avdImage.drawable as AnimatedVectorDrawable
////            animation.start()
//
//            val imageView = findViewById<ImageView>(R.id.animatedBackground)
////            back.setImageResource(R.drawable.vector)
////            imageView.setImageDrawable(animatedVectorDrawable)
////            animatedVectorDrawable.start()
//
//            imageView.setImageResource(R.drawable.)
//            val animation = imageView.drawable as AnimatedVectorDrawable
//            animation.start()


            //次のActivityへ
            startActivity(Intent(this@NewgameActivity, MainActivity::class.java))
        }
    }
}





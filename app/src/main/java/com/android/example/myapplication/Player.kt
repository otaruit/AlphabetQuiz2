package com.android.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle

class Player: AppCompatActivity(){
    var avator: String = "character_ningyo_01_blue_purple"
    private var characterNum: Int = 0
    var name: String = "ななしのごんべ"
    var score: Int = 0
    var level: Int = 1

    init {
        getPlayerInformation()

        if (this.characterNum == 0) {
            when (level) {
                0 -> this.avator = "character_ningyo_01_blue_purple"
//            1 -> lottie.setAnimation(R.raw.alien_rain)
//            2 -> lottie.setAnimation(R.raw.alien_reading)
//            3 -> lottie.setAnimation(R.raw.alien_star_eye)
//            4 -> lottie.setAnimation(R.raw.alien_going_space)
//            else -> lottie.setAnimation(R.raw.alien_love)
            }
        } else {
            when (level) {
                0 -> this.avator = "character_ningyo_01_blue_purple"
//            1 -> lottie.setAnimation(R.raw.alien_rain)
//            2 -> lottie.setAnimation(R.raw.alien_reading)
//            3 -> lottie.setAnimation(R.raw.alien_star_eye)
//            4 -> lottie.setAnimation(R.raw.alien_going_space)
//            else -> lottie.setAnimation(R.raw.alien_love)
            }
        }
        checkLevelUp()
    }


//    fun increaseScore(points: Int) {
//        score += points
//        checkLevelUp()
//    }


    private fun checkLevelUp() {
        val levelThresholds = listOf(10, 30, 60, 120, 200) // レベルごとの必要経験値のリスト

        while (level < levelThresholds.size && score >= levelThresholds[level - 1]) {
            level++
        }
    }

    private fun getPlayerInformation() {
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        name = prefs.getString("userName", "ななしのごんべ").toString()
        characterNum = prefs.getInt("characterNum", 0)
        score = prefs.getInt("totalScore", 0)
    }
}
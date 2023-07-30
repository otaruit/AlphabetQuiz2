package com.android.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView
import com.android.example.myapplication.databinding.ActivityMainBinding

class Judgement {
    private lateinit var binding: ActivityMainBinding

    // 解答ボタンが押されたら呼ばれる
    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.O)
    fun showJudgeEffect(resources:Resources,applicationContext:Context,boolean: Boolean) {

        val judgeAnimation: LottieAnimationView = binding.lottieJudge
        var soundResource: String? = null

        if (boolean) {

            judgeAnimation.setAnimation(R.raw.correct_answer)
            judgeAnimation.layoutParams.width = 700 // 幅を指定
            judgeAnimation.layoutParams.height = 700 // 高さを指定
            judgeAnimation.requestLayout() // レイアウトの再計算を要求

            soundResource = "correct"

        } else {
            judgeAnimation.setAnimation(R.raw.incorrect_animation)
            soundResource = "incorrect"
        }

//        val mediaResource = resources.getIdentifier(soundResource, "raw", context)
//        val mediaPlayer = MediaPlayer.create(applicationContext, mediaResource)
//        mediaPlayer.start()

        // アニメーションの開始
        judgeAnimation.playAnimation()

        judgeAnimation.postDelayed({
            judgeAnimation.cancelAnimation()
            judgeAnimation.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    judgeAnimation.visibility = View.GONE
                }
        }, 1500)


    }

}
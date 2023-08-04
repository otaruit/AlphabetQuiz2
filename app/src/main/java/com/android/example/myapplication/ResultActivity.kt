package com.android.example.myapplication

import Player
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.example.myapplication.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var player: Player
    private var totalScore: Int = 0
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val activityName:String? = intent.getStringExtra("FROM_ACTIVITY_NAME")
        val points = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)
        val prefs = getSharedPreferences("userInformation", MODE_PRIVATE)
        player = Player(prefs)


        val navigationFragment = activityName?.let { NavigationFragment.newInstance(it) }
        val scoreFragment = ScoreFragment.newInstance(points)
        val itemFragment = ItemFragment()

        if (navigationFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigationFragment, navigationFragment)
                .replace(R.id.scoreFragment, scoreFragment)
                .replace(R.id.itemFragment, itemFragment)
                .commit()
        }


//        showApplause()
//        displayScore(points)
//        getPlayer()

//        // 「もう一度」ボタン
//       when (activityName) {
//            "one_alphabet" ->       // 「もう一度」ボタン
//                binding.tryAgainBtn.setOnClickListener {
//                    startActivity(Intent(this@ResultActivity, AlphabetQuizActivity::class.java))
//                }
//            else -> {    binding.tryAgainBtn.setOnClickListener {
//                startActivity(Intent(this@ResultActivity, WordQuizActivity::class.java))
//            }}
//        }
//
//        // 「タイトルへもどる」ボタン
//        binding.backToTitle.setOnClickListener {
//            startActivity(Intent(this@ResultActivity, TitleActivity::class.java))
//        }

    }


//    fun showMainView(view: View) {
//        val itemView = findViewById<LinearLayout>(R.id.item)
//        val fadeOutAnimation = AlphaAnimation(1.0f, 0.0f)
//        fadeOutAnimation.duration = 1000
//        itemView.startAnimation(fadeOutAnimation)
//
//        // Optionally, set a listener to perform actions after the animation finishes
//        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//                // Animation started, do nothing here
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                // Animation ended, hide the LinearLayout after the fade-out animation
//                itemView.visibility = View.GONE
//                binding.afterAnimation.visibility = View.VISIBLE
//
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {
//                // Animation repeated, do nothing here
//            }
//        })

//        val itemView = findViewById<LinearLayout>(R.id.item)
//        // Create a fade-out animation
//        val fadeOutAnimation = AlphaAnimation(1.0f, 0.0f)
//        fadeOutAnimation.duration = 1000
//        itemView.startAnimation(fadeOutAnimation)

    }

//    private fun showApplause() {
//        val treasure: LottieAnimationView = binding.lottieTreasure
//        treasure.setAnimation(R.raw.congulatulation)
//
//        var soundResource: String? = null
//        soundResource = "level_up"
//
//        val mediaResource = resources.getIdentifier(soundResource, "raw", packageName)
//        val mediaPlayer = MediaPlayer.create(applicationContext, mediaResource)
//        mediaPlayer.start()
//
//        treasure.playAnimation()
//
//        treasure.addAnimatorListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                treasure.animate()
//                    .alpha(0f)
//                    .setDuration(2000)
//                    .withEndAction {
//                        treasure.visibility = View.GONE
//
//                    }
//            }
//        })
//
//// Post a delayed action to cancel the animation after 1500 ms
//        treasure.postDelayed({
//            treasure.cancelAnimation()
//            // Show the button after the animation finishes
//            val itemView: LinearLayout = binding.item
//            itemView.visibility = View.VISIBLE
//        }, 3000)
//    }

    // 結果を表示
//    private fun displayScore(point: Int) {
//        // スコアをセット
//        binding.resultLabel.text = getString(R.string.result_score, point)
//
//        //アニメーション表示
//        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 1.5f)
//        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 1.5f)
//        val animator =
//            ObjectAnimator.ofPropertyValuesHolder(binding.resultLabel, inflateX, inflateY).apply {
//                duration = 1000
//            }
//        animator.start()
//
//        showProgressBar(point)
//    }
//
//    @SuppressLint("DiscouragedApi", "SetTextI18n")
//    private fun getPlayer() {
//        binding.labelLevel.text = "Lv.${player.level}"
//        binding.totalScore.text = "${player.totalScore}"
//        binding.labelName.text = "${player.name}ちゃん"
//
//        binding.yourAvatar.setImageResource(player.imgResources)
//        moveCharacter()
//    }
//
//    // トータルスコア表示
//    private fun showProgressBar(score: Int) {
//        // プログレスバー設定
//        var bar: ProgressBar = findViewById(R.id.progressBar1)
//        bar.max = player.levelThresholds[level - 1]
//
//        val anim = ProgressBarAnimation(
//            bar,
//            (player.totalScore - score).toFloat(),
//            player.totalScore.toFloat()
//        )
//        anim.duration = 2000
//        bar.startAnimation(anim)
//    }
//
//    // キャラクターを動かす
//    private fun moveCharacter() {
//        val character: ImageView = binding.yourAvatar
//        val animation = AnimationUtils.loadAnimation(this, R.anim.character_move_anim)
//        character.startAnimation(animation)
//    }
//}
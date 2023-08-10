package com.android.example.myapplication

import Player
import SharedViewModel
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.example.myapplication.databinding.FragmentScoreNavBinding
import java.lang.reflect.Modifier

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "points"
private const val ARG_PARAM2 = "activityName"
private const val ARG_PARAM3 = "booleanLevelUp"

/**
 * A simple [Fragment] subclass.
 * Use the [ScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreNavFragment : Fragment() {

    private lateinit var binding: FragmentScoreNavBinding
    private lateinit var player: Player


    // TODO: Rename and change types of parameters
    private var points: Int = 0
    private var activityName: String? = null
    private var booleanLevelUp: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            points = it.getInt(ARG_PARAM1)
            activityName = it.getString(ARG_PARAM2)
            booleanLevelUp = it.getBoolean(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        player = Player(prefs)

//        binding.labelLevel.text = "Lv.${player.level}"
        binding.totalScore.text = "${player.totalScore}"
        binding.labelName.text = "${player.name}ちゃん"

//        binding.yourAvatar.setImageResource(player.imgResources)
        binding.resultLabel.text = points.toString()

//
//        if (booleanLevelUp){
//            viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
//            viewModel.isForegroundChanged.observe(viewLifecycleOwner, Observer { isForegroundChanged ->
//                if (isForegroundChanged) {
//                    // foreground属性を変更
//                    binding.scoreNavContainer.foreground = null// 新しいforeground Drawable
//
//                    displayScore(points)
//                    moveCharacter()
//                }
//        })
//        }else{
//            binding.scoreNavContainer.foreground = null// 新しいforeground Drawable
//            displayScore(points)
//            moveCharacter()
//        }



        when (activityName) {
            "one_alphabet" -> {
                binding.tryAgainBtn.setOnClickListener {
                    startActivity(Intent(requireContext(), AlphabetQuizActivity::class.java))
                }
            }
            else -> {
                binding.tryAgainBtn.setOnClickListener {
                    startActivity(Intent(requireContext(), WordQuizActivity::class.java))
                }
            }
        }
        binding.backToTitle.setOnClickListener {
            startActivity(Intent(requireContext(), TitleActivity::class.java))
        }

    }


//    private fun displayScore(point: Int) {
//        //アニメーション表示
//        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 1.5f)
//        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 1.5f)
//        val animator =
//            ObjectAnimator.ofPropertyValuesHolder(binding.resultLabel, inflateX, inflateY).apply {
//                duration = 1000
//            }
//        animator.start()
//        showProgressBar(point)
//    }

//    @SuppressLint("DiscouragedApi", "SetTextI18n")
//    private fun getPlayer() {
//        binding.labelLevel.text = "Lv.${player.level}"
//        binding.totalScore.text = "${player.totalScore}"
//        binding.labelName.text = "${player.name}ちゃん"
//
//        binding.yourAvatar.setImageResource(player.imgResources)
//
//    }

//    // トータルスコア表示
//    private fun showProgressBar(score: Int) {
//        // プログレスバー設定
//        var bar: ProgressBar = binding.progressBar1
//        bar.max = player.levelThresholds[player.level - 1]
//
//        val anim = ProgressBarAnimation(
//            bar,
//            (player.totalScore - score).toFloat(),
//            player.totalScore.toFloat()
//        )
//        anim.duration = 2000
//        bar.startAnimation(anim)
//    }

//    // キャラクターを動かす
//    private fun moveCharacter() {
//        val character: ImageView = binding.yourAvatar
//        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.character_move_anim)
//        character.startAnimation(animation)
//    }

    companion object {
        private const val ARG_PARAM1 = "points"

        @JvmStatic
        fun newInstance(param1: Int, param2: String,param3: Boolean) =
            ScoreNavFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putBoolean(ARG_PARAM3, param3)
                }
            }
    }
}
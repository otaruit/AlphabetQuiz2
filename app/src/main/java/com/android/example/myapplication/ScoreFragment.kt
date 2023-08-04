package com.android.example.myapplication

import Player
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.example.myapplication.databinding.FragmentScoreBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "points"

/**
 * A simple [Fragment] subclass.
 * Use the [ScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreFragment : Fragment() {

    private lateinit var binding: FragmentScoreBinding
    private lateinit var player: Player

    // TODO: Rename and change types of parameters
    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            points = it.getInt(ARG_PARAM1)
        }

        binding = FragmentScoreBinding.inflate(layoutInflater)

        val prefs = requireContext().getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        player = Player(prefs)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayScore(points)
        getPlayer()
    }
    private fun displayScore(point: Int) {
        // スコアをセット
        binding.resultLabel.text = getString(R.string.result_score, point)

        //アニメーション表示
        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 1.5f)
        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 1.5f)
        val animator =
            ObjectAnimator.ofPropertyValuesHolder(binding.resultLabel, inflateX, inflateY).apply {
                duration = 1000
            }
        animator.start()

        showProgressBar(point)
    }

    @SuppressLint("DiscouragedApi", "SetTextI18n")
    private fun getPlayer() {
        binding.labelLevel.text = "Lv.${player.level}"
        binding.totalScore.text = "${player.totalScore}"
        binding.labelName.text = "${player.name}ちゃん"

        binding.yourAvatar.setImageResource(player.imgResources)
        moveCharacter()
    }

    // トータルスコア表示
    private fun showProgressBar(score: Int) {
        // プログレスバー設定
        var bar: ProgressBar = binding.progressBar1
        bar.max = player.levelThresholds[player.level - 1]

        val anim = ProgressBarAnimation(
            bar,
            (player.totalScore - score).toFloat(),
            player.totalScore.toFloat()
        )
        anim.duration = 2000
        bar.startAnimation(anim)
    }

    // キャラクターを動かす
    private fun moveCharacter() {
        val character: ImageView = binding.yourAvatar
        val animation = AnimationUtils.loadAnimation(context, R.anim.character_move_anim)
        character.startAnimation(animation)
    }
    companion object {
        private const val ARG_PARAM1 = "points"

        @JvmStatic
        fun newInstance(param1: Int) =
            ScoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}
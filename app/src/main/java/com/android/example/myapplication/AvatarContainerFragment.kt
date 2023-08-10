package com.android.example.myapplication

import SharedViewModel
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.android.example.myapplication.databinding.FragmentAvatarContainerBinding
import com.android.example.myapplication.databinding.FragmentScoreNavBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "avatar"
private const val ARG_PARAM2 = "totalScore"
private const val ARG_PARAM3 = "item1"
private const val ARG_PARAM4 = "item2"
private const val ARG_PARAM5 = "item3"
private const val ARG_PARAM6 = "item4"
private const val ARG_PARAM7 = "item5"
private const val ARG_PARAM8 = "level"
private const val ARG_PARAM9 = "name"

/**
 * A simple [Fragment] subclass.
 * Use the [AvatarContainerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AvatarContainerFragment : Fragment() {
    private lateinit var binding: FragmentAvatarContainerBinding
    private lateinit var viewModel: SharedViewModel
    // TODO: Rename and change types of parameters
    private var param1: Int = 0
    private var param2: Int = 0
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private var param6: String? = null
    private var param7: String? = null
    private var param8: Int = 0
    private var param9: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
            param5 = it.getString(ARG_PARAM5)
            param6 = it.getString(ARG_PARAM6)
            param7 = it.getString(ARG_PARAM7)
            param8 = it.getInt(ARG_PARAM8)
            param9 = it.getString(ARG_PARAM9)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAvatarContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.labelLevel.text = "Lv.${param8}"
//        binding.labelName.text = "${param9}ちゃん"

        var imgResources :Int = 0
        when (param1) {
            0 -> imgResources = R.drawable.character_hime_01_blue_gold
            1 -> imgResources = R.drawable.character_yusha_01_red
            2 -> imgResources = R.drawable.character_tenshi_brown
        }
        binding.yourAvatar.setImageResource(imgResources)


        if (booleanLevelUp){
            viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            viewModel.isForegroundChanged.observe(viewLifecycleOwner, Observer { isForegroundChanged ->
                if (isForegroundChanged) {
                    // foreground属性を変更
                    binding.scoreNavContainer.foreground = null// 新しいforeground Drawable

                    displayScore(points)
                    moveCharacter()
                }
        })
        }else{
            binding.scoreNavContainer.foreground = null// 新しいforeground Drawable
            displayScore(points)
            moveCharacter()
        }

    }

    private fun displayScore(point: Int) {
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
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.character_move_anim)
        character.startAnimation(animation)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AvatarContainerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(
            param1: Int,
            param2: Int,
            param3: String,
            param4: String,
            param5: String,
            param6: String,
            param7: String,
            param8: Int,
            param9: String
        ) =
            AvatarContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAM4, param4)
                    putString(ARG_PARAM5, param5)
                    putString(ARG_PARAM6, param6)
                    putString(ARG_PARAM7, param7)
                    putInt(ARG_PARAM8, param8)
                    putString(ARG_PARAM9, param9)
                }
            }
    }
}
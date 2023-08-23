package com.android.example.myapplication

import Player
import SharedViewModel
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.example.myapplication.databinding.FragmentAvatarContainerBinding
import com.android.example.myapplication.databinding.FragmentScoreNavBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM6 = "booleanLevelUp"

/**
 * A simple [Fragment] subclass.
 * Use the [AvatarContainerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AvatarContainerFragment : Fragment() {

    private lateinit var player: Player
    private lateinit var binding: FragmentAvatarContainerBinding
    private lateinit var viewModel: SharedViewModel

    private var param6: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param6 = it.getBoolean(ARG_PARAM6)
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

        val prefs = requireContext().getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        val activity = requireActivity()

        player = Player(prefs, activity)
        itemSet(player.itemList)

        binding.labelLevel.text = "Lv.${player.level}"
        binding.labelName.text = "${player.name}ちゃん"

        var imgResources: Int = 0
        when (player.avatar) {
            0 -> imgResources = R.drawable.character_hime_01_blue_gold
            1 -> imgResources = R.drawable.character_yusha_01_red
            2 -> imgResources = R.drawable.character_tenshi_brown
        }
        binding.yourAvatar.setImageResource(imgResources)

        if (param6) {
            viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            viewModel.isForegroundChanged.observe(
                viewLifecycleOwner,
                Observer { isForegroundChanged ->
                    if (isForegroundChanged) {
                        showProgressBar(player.totalScore)
                        moveCharacter()
                    }
                })
        } else {
            showProgressBar(player.totalScore)
            moveCharacter()
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun itemSet(formattedItems: List<String>) {
        for (formattedItem in formattedItems) {
            var resourceId = 0
            if (formattedItem != "item_0_0") {
                when (formattedItem) {
                    "item_1_1" -> {
                        resourceId = R.drawable.yama_01
                        var item = binding.itemBuilding
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_1_2" -> {
                        resourceId = R.drawable.shiro_02_brown_roof_red
                        var item = binding.itemBuilding
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_2_1" -> {
                        resourceId = R.drawable.takibi_on
                        var item = binding.itemGround1
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                }

            }
        }
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
            param6: Boolean
        ) =
            AvatarContainerFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM6, param6)
                }
            }
    }
}
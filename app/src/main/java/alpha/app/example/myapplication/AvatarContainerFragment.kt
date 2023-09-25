package alpha.app.example.myapplication

import Player
import SharedViewModel
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import alpha.app.example.myapplication.databinding.FragmentAvatarContainerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "booleanLevelUp"
private const val ARG_PARAM2 = "points"

/**
 * A simple [Fragment] subclass.
 * Use the [AvatarContainerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AvatarContainerFragment : Fragment() {

    private lateinit var player: Player
    private lateinit var binding: FragmentAvatarContainerBinding
    private lateinit var viewModel: SharedViewModel

    private var param1: Boolean = false
    private var param2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getBoolean(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
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
        player.itemSet(activity)

        itemSet(player

            .itemList)

        binding.labelLevel.text = "Lv.${player.level}"
        binding.labelName.text = "${player.name}ちゃん"

        var imgResources: Int = 0
        when (player.avatar) {
            0 -> imgResources = R.drawable.character_hime_01_blue_gold
            1 -> imgResources = R.drawable.character_yusha_01_red
            2 -> imgResources = R.drawable.character_tenshi_brown
        }
        binding.yourAvatar.setImageResource(imgResources)

        if (param1) {
            viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            viewModel.isForegroundChanged.observe(
                viewLifecycleOwner,
                Observer { isForegroundChanged ->
                    if (isForegroundChanged) {
                        showProgressBar(param2)
                        moveCharacter()
                    }
                })
        } else {
            showProgressBar(param2)
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
                        val item = binding.itemBuilding
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_1_2" -> {
                        resourceId = R.drawable.shiro_02_brown_roof_red
                        val item = binding.itemBuilding
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_2_1" -> {
                        resourceId = R.drawable.takibi_on
                        val item = binding.itemGround1
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_2_2" -> {
                        resourceId = R.drawable.cosmos_lightpink
                        val item = binding.itemGround1
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_3_1" -> {
                        resourceId = R.drawable.snowman_red
                        val item = binding.itemGround2
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_3_2" -> {
                        resourceId = R.drawable.kingchair_gold_red
                        val item = binding.itemGround2
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_4_1" -> {
                        resourceId = R.drawable.kirikabu_01
                        val item = binding.itemGround3
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_4_2" -> {
                        resourceId = R.drawable.kusa_02
                        val item = binding.itemGround3
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_5_1" -> {
                        resourceId = R.drawable.ufo_08
                        val item = binding.itemFlying1
                        item.setImageResource(resourceId)
                        item.visibility = View.VISIBLE
                    }
                    "item_5_2" -> {
                        resourceId = R.drawable.star_lightyellow
                        val item = binding.itemFlying1
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
        val bar: ProgressBar = binding.progressBar1
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
            param1: Boolean,
            param2: Int
        ) =
            AvatarContainerFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }
}
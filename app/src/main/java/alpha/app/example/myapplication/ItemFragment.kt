package alpha.app.example.myapplication

import Player
import SharedViewModel
import TextWriter
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import alpha.app.example.myapplication.databinding.FragmentItemBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "level"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragment : Fragment() {
    private lateinit var player: Player
    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: FragmentItemBinding

    // TODO: Rename and change types of parameters
    private var level: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            level = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        val activity = requireActivity()
        player = Player(prefs, activity)

        val itemName = itemGet(player)
        itemSave(activity, itemName)

        val buttonGet = binding.buttonGet
        buttonGet.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()

            viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            viewModel.isForegroundChanged.value = true
        }
    }

    private fun itemGet(player: Player): String {
        val listSize = player.itemList.size
        var resourceId = 0

        if (listSize != 5) {

            val randomInt = (1..2).random()

            when (listSize) {
                0 -> {
                    when (randomInt) {
                        1 -> {
                            resourceId = R.drawable.yama_01
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_1_1"
                        }
                        2 -> {
                            resourceId = R.drawable.shiro_02_brown_roof_red
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_1_2"
                        }
                    }
                }
                1 -> {
                    when (randomInt) {
                        1 -> {
                            resourceId = R.drawable.takibi_on
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_2_1"
                        }
                        2 -> {
                            resourceId = R.drawable.cosmos_lightpink
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_2_2"
                        }
                    }
                }
                2 -> {
                    when (randomInt) {
                        1 -> {
                            resourceId = R.drawable.snowman_red
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_3_1"
                        }
                        2 -> {
                            resourceId = R.drawable.kingchair_gold_red
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_3_2"
                        }
                    }
                }
                3 -> {
                    when (randomInt) {
                        1 -> {
                            resourceId = R.drawable.kirikabu_01
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_4_1"
                        }
                        2 -> {
                            resourceId = R.drawable.kusa_02
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_4_2"
                        }
                    }
                }
                4 -> {
                    when (randomInt) {
                        1 -> {
                            resourceId = R.drawable.ufo_08
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_5_1"
                        }
                        2 -> {
                            resourceId = R.drawable.star_lightyellow
                            val item = binding.treasure
                            item.setImageResource(resourceId)
                            return "item_5_2"
                        }
                    }
                }


            }
        }
        return ""
    }

    private fun itemSave(context: Context, itemName: String) {
        val textWriter = TextWriter(context as Activity)
        player.itemList += itemName
        textWriter.saveText(player.itemList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(level: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, level)
                }
            }
    }
}
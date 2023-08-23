package com.android.example.myapplication

import Player
import SharedViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.myapplication.databinding.FragmentItemBinding
import java.security.AccessController.getContext
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
        player = Player(prefs,activity)

        val treasure = binding.treasure
        treasure.setImageResource(player.imgResources)

        val buttonGet = binding.buttonGet
        buttonGet.setOnClickListener {
            // Fragmentを非表示にする
            viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
            viewModel.isForegroundChanged.value = true

            val transaction = parentFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()
        }
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
package com.android.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.example.myapplication.databinding.FragmentNavigationBinding
import com.android.example.myapplication.databinding.FragmentScoreBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "activityName"

/**
 * A simple [Fragment] subclass.
 * Use the [NavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavigationFragment : Fragment() {
    private lateinit var binding: FragmentNavigationBinding

    // TODO: Rename and change types of parameters
    private var activityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            activityName = it.getString(activityName)
        }

        binding = FragmentNavigationBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)

    }

    // Viewが生成し終わった時に呼ばれるメソッド
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bundleから値を取得
//        val value = arguments?.getString("key_name")
        // 取得した値を使ってFragment内での処理を行う

        // 「もう一度」ボタン
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
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NavigationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            NavigationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}

package com.android.example.myapplication

import Player
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.android.example.myapplication.databinding.FragmentItemBinding
import com.android.example.myapplication.databinding.FragmentNavigationBinding
import com.android.example.myapplication.databinding.FragmentScoreBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragment : Fragment() {

    private lateinit var binding: FragmentItemBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        binding = FragmentItemBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)

    }

    // Viewが生成し終わった時に呼ばれるメソッド
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showApplause()

        val itemView = binding.item
        val fadeOutAnimation = AlphaAnimation(1.0f, 0.0f)
        fadeOutAnimation.duration = 1000
        itemView.startAnimation(fadeOutAnimation)

        // Optionally, set a listener to perform actions after the animation finishes
        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Animation started, do nothing here
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Animation ended, hide the LinearLayout after the fade-out animation
                itemView.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Animation repeated, do nothing here
            }
        })
    }

    private fun showApplause() {
        val treasure: LottieAnimationView = binding.lottieTreasure
        treasure.setAnimation(R.raw.congulatulation)

        var soundResource: String? = null
        soundResource = "level_up"

        val mediaResource = resources.getIdentifier(soundResource, "raw", requireContext().packageName)
        val mediaPlayer = MediaPlayer.create(requireContext(), mediaResource)
        mediaPlayer.start()

        treasure.playAnimation()

        treasure.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                treasure.animate()
                    .alpha(0f)
                    .setDuration(2000)
                    .withEndAction {
                        treasure.visibility = View.GONE

                    }
            }
        })

// Post a delayed action to cancel the animation after 1500 ms
        treasure.postDelayed({
            treasure.cancelAnimation()
            // Show the button after the animation finishes
            val itemView: LinearLayout = binding.item
            itemView.visibility = View.VISIBLE
        }, 3000)
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
                fun newInstance(param1: String, param2: String) =
                    ItemFragment().apply {
                        arguments = Bundle().apply {
                            putString(ARG_PARAM1, param1)
                            putString(ARG_PARAM2, param2)
                        }
                    }
            }
        }
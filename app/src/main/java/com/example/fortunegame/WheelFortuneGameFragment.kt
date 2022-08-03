package com.example.fortunegame

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fortunegame.databinding.FragmentWheelFortuneFameBinding
import kotlin.random.Random

class WheelFortuneFameFragment : Fragment() {

    private val sectors = arrayOf(700, 1000, 100, 200, 500)
    private val sectorDegrees = sectors.clone()
    private val singleSectorDegree = 360 / sectors.size
    private var isSpinning = false

    private var _binding: FragmentWheelFortuneFameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        _binding = FragmentWheelFortuneFameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // enter user score here according to logic
        binding.userScoreCount.text = "55"

        initExitBtn()
        getDegreeForSectors()
        binding.btnGameFortuneSpin.setOnClickListener {
            if (!isSpinning) {
                spin()
                isSpinning = true
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun initExitBtn() {
        binding.btnImgExitVheelGame.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun spin() {
        val winnerNumber = Random.nextInt(sectors.size - 1)

        //calculate number of degrees for set pointer to correct sector in UI
        val needAddRotate = (360 - winnerNumber * singleSectorDegree).toFloat()
        val rotateAnimation = RotateAnimation(
            0f,
            (360f * sectors.size) + needAddRotate,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.apply {
            duration = 1000
            fillAfter = true
            interpolator = DecelerateInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    val userResult = sectors[winnerNumber]
                    Toast.makeText(
                        requireContext(),
                        "It is $userResult} points",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    isSpinning = false
                }
                override fun onAnimationRepeat(p0: Animation?) {
                }
            })
            binding.imgWheelElementMain.startAnimation(rotateAnimation)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun getDegreeForSectors() {
        for (i in sectors.indices) {
            sectorDegrees[i] = (i + 1) * singleSectorDegree
        }
    }
}
package com.example.fortunegame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fortunegame.databinding.FragmentGreedyWolfGameFragmentBinding
import com.example.fortunegame.databinding.FragmentWheelFortuneFameBinding
import kotlinx.coroutines.delay
import kotlin.random.Random

class GreedyWolfGameFragment : Fragment() {

    private fun getNextRandomelement(): Int {
        return allElementsSlotMachine[Random.nextInt(allElementsSlotMachine.size)]
    }

    private val allElementsSlotMachine = listOf(
        R.drawable.symbol_1game_wolf,
        R.drawable.symbol_2game_1,
        R.drawable.symbol_3game_1,
        R.drawable.symbol_4game_1,
        R.drawable.symbol_5game_1,
        R.drawable.symbol_6game_1,
        R.drawable.symbol_7game_1,
        R.drawable.symbol_8game_1,
        R.drawable.symbol_9game_1,
    )
    val nextElementRandom = getNextRandomelement()

    private var _binding: FragmentGreedyWolfGameFragmentBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreedyWolfGameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("LOOO", "current y ${binding.imgLeftBottom.y}")
        Log.d("LOOO", "imgLeftBottom isFocusable ${binding.imgLeftBottom.isFocused}")

        binding.btnPlayGame1.setOnClickListener {
            binding.imgLeftBottom.animate().translationYBy(binding.imgLeftBottom.height.toFloat() ).duration = 300L
            binding.imgLeftCenter.animate().translationYBy(binding.imgLeftCenter.height.toFloat() ).duration = 300L
            binding.imgLeftTop.animate().translationYBy(binding.imgLeftTop.height.toFloat() ).duration = 300L

            Log.d("LOOO", "current y ${binding.imgLeftBottom.y}")
            Log.d("LOOO", "imgLeftBottom isFocusable ${binding.imgLeftBottom.isFocused}")

            binding.frameContainer.animate().translationYBy(binding.frameContainer.height.toFloat() ).duration = 300L


        }



        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
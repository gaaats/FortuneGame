package com.example.fortunegame

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fortunegame.databinding.FragmentGreedyWolfGameFragmentBinding
import com.example.fortunegame.databinding.SingleSlotElementBinding
import com.example.fortunegame.utils.SlotElement
import com.example.fortunegame.utils.SlotListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GreedyWolfGameFragment : Fragment() {

    private val slotListAdapterLeft = SlotListAdapter()
    private val slotListAdapterCenter = SlotListAdapter()
    private val slotListAdapterRight = SlotListAdapter()

    private var _binding: FragmentGreedyWolfGameFragmentBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        _binding = FragmentGreedyWolfGameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val linearLayoutManagerLeft = binding.recVLeft.layoutManager as LinearLayoutManager
        val linearLayoutManagerCenter = binding.recVCenter.layoutManager as LinearLayoutManager
        val linearLayoutManagerRight = binding.recVRight.layoutManager as LinearLayoutManager

        disableScrollingRecVeivs()
        initAdaptersRecV()
        submitListsForRecV()

        binding.btnPlayGame1.setOnClickListener {
            initScrollingSlotMachine(linearLayoutManagerLeft, 8, 12)
            initScrollingSlotMachine(linearLayoutManagerCenter, 12, 18)
            initScrollingSlotMachine(linearLayoutManagerRight, 20, 27)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun submitListsForRecV() {
        slotListAdapterLeft.submitList(generateSlotList().shuffled())
        slotListAdapterRight.submitList(generateSlotList().shuffled())
        slotListAdapterCenter.submitList(generateSlotList().shuffled())
    }

    private fun initAdaptersRecV() {
        binding.recVLeft.adapter = slotListAdapterLeft
        binding.recVRight.adapter = slotListAdapterCenter
        binding.recVCenter.adapter = slotListAdapterRight
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun disableScrollingRecVeivs() {
        binding.recVLeft.setOnTouchListener { _, _ -> true }
        binding.recVRight.setOnTouchListener { _, _ -> true }
        binding.recVCenter.setOnTouchListener { _, _ -> true }
    }

    private fun generateSlotList(): List<SlotElement> {
        val listOfImages = generateList()
        val preList = mutableListOf<SlotElement>()
        for (i in 1..50) {
            preList.add(
                SlotElement(
                    Random.nextInt(Int.MAX_VALUE),
                    listOfImages.random(),
                    Random.nextInt(10)
                )
            )
        }
        return preList
    }

    private fun generateList(): List<Int> {
        return listOf(
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
    }

    private fun initScrollingSlotMachine(
        linearLayoutManager: LinearLayoutManager,
        minNumberScrolling: Int,
        maxNumberScrolling: Int
    ) {
        lifecycleScope.launch {
            var timeForDelayLeft = 100L
            for (i in 1..Random.nextInt(minNumberScrolling, maxNumberScrolling)) {
                linearLayoutManager.scrollToPositionWithOffset(i, 0)
                delay(timeForDelayLeft)
                timeForDelayLeft += 5
            }
        }
    }
}
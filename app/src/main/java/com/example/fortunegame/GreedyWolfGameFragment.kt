package com.example.fortunegame

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fortunegame.databinding.FragmentGreedyWolfGameFragmentBinding
import com.example.fortunegame.utils.SlotListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Singleton
import kotlin.random.Random

@AndroidEntryPoint
@Singleton
class GreedyWolfGameFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()

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

        mainViewModel.currentBet.observe(viewLifecycleOwner) {
            binding.tvUserBetCount.text = it.toString()
        }

        mainViewModel.currentWinGreedy.observe(viewLifecycleOwner) {
            binding.tvWinCount.text = it.toString()
        }

        mainViewModel.currentBalance.observe(viewLifecycleOwner) {
            binding.tvBalanceCount.text = it.toString()
        }

        binding.btnPlusGame1.setOnClickListener {
            if (mainViewModel.currentBet.value!! < 200) {
                mainViewModel.changeBet(10)
            }
        }

        binding.btnMinusGame1.setOnClickListener {
            if (mainViewModel.currentBet.value!! > 10) {
                mainViewModel.changeBet(-10)
            }
        }

        initExitBtn()
        val linearLayoutManagerLeft = binding.recVLeft.layoutManager as LinearLayoutManager
        val linearLayoutManagerCenter = binding.recVCenter.layoutManager as LinearLayoutManager
        val linearLayoutManagerRight = binding.recVRight.layoutManager as LinearLayoutManager

        disableScrollingRecVeivs()
        initAdaptersRecV()
        submitListsForRecV()

        binding.btnPlayGame1.setOnClickListener {
            // just change time of each scrolling recViev for better performance
            initScrollingSlotMachine(linearLayoutManagerLeft, 8, 12)
            initScrollingSlotMachine(linearLayoutManagerCenter, 12, 18)
            initScrollingSlotMachine(linearLayoutManagerRight, 20, 27)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initExitBtn() {
        binding.btnGame1ImgExit.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun submitListsForRecV() {
        mainViewModel.leftList.observe(viewLifecycleOwner) {
            slotListAdapterLeft.submitList(it)
        }
        mainViewModel.rightList.observe(viewLifecycleOwner) {
            slotListAdapterRight.submitList(it)
        }
        mainViewModel.centerList.observe(viewLifecycleOwner) {
            slotListAdapterCenter.submitList(it)
        }
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

    private fun initScrollingSlotMachine(
        linearLayoutManager: LinearLayoutManager,
        minNumberScrolling: Int,
        maxNumberScrolling: Int
    ) {
        lifecycleScope.launch {
            val numberTop = Random.nextInt(minNumberScrolling, maxNumberScrolling)
            var timeForDelayLeft = 100L
            for (i in 1..numberTop) {
                linearLayoutManager.scrollToPositionWithOffset(i, 0)
                delay(timeForDelayLeft)
                timeForDelayLeft += 5
            }
            if (maxNumberScrolling == 27) {
                checkResult()
            }
        }
    }

    private fun checkResult() {
        mainViewModel.checkResultGreedy()
    }
}
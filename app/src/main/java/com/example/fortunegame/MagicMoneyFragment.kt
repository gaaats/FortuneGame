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
import com.example.fortunegame.databinding.FragmentMagicMoneyBinding
import com.example.fortunegame.utils.SlotElement
import com.example.fortunegame.utils.SlotListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Singleton
import kotlin.random.Random

@AndroidEntryPoint
@Singleton
class MagicMoneyFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val slotListAdapterLeft = SlotListAdapter()
    private val slotListAdapterCenter = SlotListAdapter()
    private val slotListAdapterRight = SlotListAdapter()

    private var _binding: FragmentMagicMoneyBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        _binding = FragmentMagicMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel.currentBetMoneyGame.observe(viewLifecycleOwner) {
            binding.tvUserBetCount.text = it.toString()
        }

        mainViewModel.currentWinMoney.observe(viewLifecycleOwner) {
            binding.tvWinCount.text = it.toString()
        }

        mainViewModel.currentBalance.observe(viewLifecycleOwner) {
            binding.tvBalanceCount.text = it.toString()
        }

        binding.btnPlusGame2.setOnClickListener {
            if (mainViewModel.currentBetMoneyGame.value!! < 200) {
                mainViewModel.changeBetMoneyGame(10)
            }
        }

        binding.btnMinusGame2.setOnClickListener {
            if (mainViewModel.currentBetMoneyGame.value!! > 10) {
                mainViewModel.changeBetMoneyGame(-10)
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
        mainViewModel.leftListMoney.observe(viewLifecycleOwner) {
            slotListAdapterLeft.submitList(it)
        }
        mainViewModel.rightListMoney.observe(viewLifecycleOwner) {
            slotListAdapterRight.submitList(it)
        }
        mainViewModel.centerListMoney.observe(viewLifecycleOwner) {
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
            var timeForDelayLeft = 100L
            for (i in 1..Random.nextInt(minNumberScrolling, maxNumberScrolling)) {
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
        mainViewModel.checkResultMoney()
    }

}
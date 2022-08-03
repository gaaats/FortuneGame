package com.example.fortunegame

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fortunegame.utils.SlotElement
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class MainViewModel @Inject constructor(private val application: Application) : ViewModel() {

    fun loadCurrentUserBalance(userBalance: Int) {
        _currentBalance.value = userBalance
    }

    private var _currentBalance = MutableLiveData<Int>()
    val currentBalance: LiveData<Int>
        get() = _currentBalance

    private var _currentWinGreedy = MutableLiveData<Int>()
    val currentWinGreedy: LiveData<Int>
        get() = _currentWinGreedy

    private var _currentWinMoney = MutableLiveData<Int>()
    val currentWinMoney: LiveData<Int>
        get() = _currentWinMoney

    private var _currentBet = MutableLiveData<Int>()
    val currentBet: LiveData<Int>
        get() = _currentBet

    private var _currentBetMoneyGame = MutableLiveData<Int>()
    val currentBetMoneyGame: LiveData<Int>
        get() = _currentBetMoneyGame

    private val _leftList = MutableLiveData<List<SlotElement>>()
    val leftList: LiveData<List<SlotElement>>
        get() = _leftList

    private val _centerList = MutableLiveData<List<SlotElement>>()
    val centerList: LiveData<List<SlotElement>>
        get() = _centerList

    private val _rightList = MutableLiveData<List<SlotElement>>()
    val rightList: LiveData<List<SlotElement>>
        get() = _rightList

    private val _leftListMoney = MutableLiveData<List<SlotElement>>()
    val leftListMoney: LiveData<List<SlotElement>>
        get() = _leftListMoney

    private val _centerListMoney = MutableLiveData<List<SlotElement>>()
    val centerListMoney: LiveData<List<SlotElement>>
        get() = _centerListMoney

    private val _rightListMoney = MutableLiveData<List<SlotElement>>()
    val rightListMoney: LiveData<List<SlotElement>>
        get() = _rightListMoney

    init {
        _currentWinGreedy.value = 0
        _currentWinMoney.value = 0
        _currentBet.value = 100
        _currentBetMoneyGame.value = 100
        generateLists()
        generateListsGameMoney()
    }


    fun changeBalance(count: Int) {

        _currentBalance.value = _currentBalance.value?.plus(count)
        Log.d("MY_TAG", "_currentBalance.value is ${_currentBalance.value}")
    }

    fun changeBet(count: Int) {
        _currentBet.value = _currentBet.value?.plus(count)
    }

    fun changeBetMoneyGame(count: Int) {
        _currentBetMoneyGame.value = _currentBetMoneyGame.value?.plus(count)
    }

    private fun generateLists() {
        _leftList.value = generateSlotList(generateList())
        _rightList.value = generateSlotList(generateList())
        _centerList.value = generateSlotList(generateList())
    }

    private fun generateListsGameMoney() {
        _leftListMoney.value = generateSlotList(generateListGameMoney())
        _rightListMoney.value = generateSlotList(generateListGameMoney())
        _centerListMoney.value = generateSlotList(generateListGameMoney())
    }

    private fun generateSlotList(map: Map<Int, Int>): List<SlotElement> {
        val preList = mutableListOf<SlotElement>()
        for (i in 1..50) {
            val keyImage = map.keys.random()
            preList.add(
                SlotElement(
                    Random.nextInt(Int.MAX_VALUE),
                    keyImage,
                    map.getOrDefault(keyImage, 0)
                )
            )
        }
        return preList
    }

    private fun generateList(): Map<Int, Int> {
        return mapOf(
            R.drawable.symbol_1game_wolf to 1,
            R.drawable.symbol_2game_1 to 2,
            R.drawable.symbol_3game_1 to 3,
            R.drawable.symbol_4game_1 to 4,
            R.drawable.symbol_5game_1 to 5,
            R.drawable.symbol_6game_1 to 6,
            R.drawable.symbol_7game_1 to 7,
            R.drawable.symbol_8game_1 to 8,
            R.drawable.symbol_9game_1 to 9,
        )
    }

    private fun generateListGameMoney(): Map<Int, Int> {
        return mapOf(
            R.drawable.symbol_1_game_money to 1,
            R.drawable.symbol_2_game_money to 2,
            R.drawable.symbol_3_game_money to 3,
            R.drawable.symbol_4_game_money to 4,
            R.drawable.symbol_5_game_money to 5,
            R.drawable.symbol_6_game_money to 6,
            R.drawable.symbol_7_game_money to 7,
            R.drawable.symbol_8_game_money to 8,
            R.drawable.symbol_9_game_money to 9,
        )
    }

    fun setCurrentWinGreedy(win: Int) {
        _currentWinGreedy.value = win
    }

    fun setCurrentWinMoney(win: Int) {
        _currentWinMoney.value = win
    }

    fun checkResultGreedy() {
        Random.nextInt(from = -10, until = 10).apply {
            setCurrentWinGreedy(currentBet.value!! * this)
            changeBalance(currentBet.value!! * this)
            Log.d("MY_TAG", "random result is${this}")
        }
    }

    fun checkResultMoney() {
        Random.nextInt(from = -10, until = 10).apply {
            setCurrentWinMoney(currentBetMoneyGame.value!! * this)
            changeBalance(currentBetMoneyGame.value!! * this)
            Log.d("MY_TAG", "random result is${this}")
        }
    }
}
package com.example.fortunegame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import com.example.fortunegame.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class MainActivity : AppCompatActivity() {

    private val sheredPref by lazy {
        getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)
    }
    private val mainVievModel by viewModels<MainViewModel>()

    private val userBalanceFromSheredPref by lazy {
        sheredPref.getInt(KEY_TO_PREF_USER_BALANCE, 0)
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onStart() {
        Log.d("MY_TAG", "it is onStart")
        super.onStart()
    }

    override fun onStop() {
        Log.d("MY_TAG", "it is onStop")
        saveUserBalance()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d("MY_TAG", "it is onCreate")
        takeUserBalance()
        setContentView(binding.root)

    }

    override fun onDestroy() {
        Log.d("MY_TAG", "it is onDestroy")
        _binding = null
        super.onDestroy()
    }

    private fun saveUserBalance() {
        mainVievModel.currentBalance.value?.also {
            Log.d("MY_TAG", "save value is ${it}")
            sheredPref.edit()
                .putInt(KEY_TO_PREF_USER_BALANCE, it)
                .apply()
        }
    }

    private fun takeUserBalance() {
        Log.d("MY_TAG", "save value is ${userBalanceFromSheredPref}")
        mainVievModel.loadCurrentUserBalance(userBalanceFromSheredPref)
    }

    companion object {
        private const val APP_PREF = "APP_PREF"
        private const val KEY_TO_PREF_USER_BALANCE = "444"
    }
}


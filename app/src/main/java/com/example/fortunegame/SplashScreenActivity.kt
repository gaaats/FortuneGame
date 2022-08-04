package com.example.fortunegame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fortunegame.SupportViewActivity.Companion.WZCCA
import com.example.fortunegame.databinding.SplashScrnBinding
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_TIME: Long = 1000
    private var _binding: SplashScrnBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private val rqzzpbnl = CoroutineScope(Dispatchers.Main + Job())
    private val xuaogbsv = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var mqvrkdzk: HelpTools

    private fun nvxadcyg(utfihhea: String) {
        startActivity(Intent(this, SupportViewActivity::class.java).apply {
            putExtra(WZCCA, utfihhea)
        })
        finish()
    }

    private fun evuzl() {
        Thread {
            Thread.sleep(2000)
            runOnUiThread {
                //Done
                // направляем дальше к играм, которые вы написали
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SplashScrnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //delete if you don`t need
        initProgBar()
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_SCREEN_TIME)

//        mqvrkdzk = HelpTools(this)
//
//        opduv()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun opduv() {
        xuaogbsv.launch {
            try {
                val utfihhea = mqvrkdzk.opduv()

                if (!utfihhea.isNullOrEmpty()) {
                    Log.d("MY_TAG", "in opduv !utfihhea.isNullOrEmpty()")
                    nvxadcyg(utfihhea)
                } else {
                    Log.d("MY_TAG", "in opduv else - evuzl")
                    evuzl()
                }
            } catch (e: Exception) {
                Log.d("MY_TAG", "in catch")
                e.printStackTrace()
                rqzzpbnl.launch {
                    initAlertDialog()
                    //DONE
                    // здесь показываем неотменяемый диалог с одной кнопкой, вроде
                    // Error has occurred. Try again?
                    // Диалог на английском, текст произвольный
                    // при нажатии на кнопку снова вызываем opduv
                }
            }
        }
    }

    private fun initAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Error has occurred. Try again?")
            .setPositiveButton("Confirm") { dialogInterface, i ->
                opduv()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun initProgBar() {
        lifecycleScope.launch {
            for (progress in 1..100) {
                withContext(Dispatchers.Main) {
                    binding.progBarSplashScrn.progress = progress
                }
                delay(SPLASH_SCREEN_TIME / 125)
            }
        }
    }
}

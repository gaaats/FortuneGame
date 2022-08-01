package com.example.fortunegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fortunegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onStart() {
        Log.d("MY_TAG", "it is onStart")
        super.onStart()
    }

    override fun onPause() {
        Log.d("MY_TAG", "it is onPause")
        super.onPause()
    }

    override fun onResume() {
        Log.d("MY_TAG", "it is onResume")
        super.onResume()
    }

    override fun onRestart() {
        Log.d("MY_TAG", "it is onRestart")
        super.onRestart()
    }

    override fun onStop() {
        Log.d("MY_TAG", "it is onStop")
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d("MY_TAG", "it is onCreate")

        setContentView(binding.root)
    }

    override fun onDestroy() {
        Log.d("MY_TAG", "it is onDestroy")
        _binding = null
        super.onDestroy()
    }
}


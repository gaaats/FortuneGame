package com.example.fortunegame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fortunegame.databinding.FragmentMenuStartBinding
import com.example.fortunegame.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("MY_TAG", "it is Fragment onCreateView ")

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MY_TAG", "it is Fragment onViewCreated ")
        binding.imgQuit.setOnClickListener {

        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        Log.d("MY_TAG", "it is Fragment onDestroy ")
        _binding = null
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        Log.d("MY_TAG", "it is Fragment onAttach ")
        super.onAttach(context)
    }

    

    override fun onDetach() {
        Log.d("MY_TAG", "it is Fragment onDetach ")
        super.onDetach()
    }

    override fun onDestroyView() {
        Log.d("MY_TAG", "it is Fragment onDestroyView ")
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MY_TAG", "it is Fragment onCreate ")
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        Log.d("MY_TAG", "it is Fragment onStart ")
        super.onStart()
    }

    override fun onResume() {
        Log.d("MY_TAG", "it is Fragment onResume ")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MY_TAG", "it is Fragment onPause ")
        super.onPause()
    }

    override fun onStop() {
        Log.d("MY_TAG", "it is Fragment onStop ")
        super.onStop()
    }


}
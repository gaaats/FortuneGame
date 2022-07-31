package com.example.fortunegame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fortunegame.databinding.ActivityMainBinding.inflate
import com.example.fortunegame.databinding.FragmentMenuStartBinding

class StartFragment : Fragment() {

    private var _binding: FragmentMenuStartBinding? = null
    private val binding get() = _binding?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
package com.example.fortunegame

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
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
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MY_TAG", "it is Fragment onViewCreated ")
        initExitBtn()
        binding.tvTermsConditions.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(LINK_PRIVACY_POLICY)
                startActivity(this)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initExitBtn() {
        binding.btnImgExitSettings.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
    companion object{
        private const val LINK_PRIVACY_POLICY = "https://bottulposter.online/agrement"
    }
}
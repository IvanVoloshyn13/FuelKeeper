package com.example.fuelkeeper.presentation.settings

import android.app.UiModeManager
import android.content.Context.UI_MODE_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.fuelkeeper.databinding.FragmentSettingsBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.card.MaterialCardView.OnCheckedChangeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val settingsViewModel: SettingsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater)
        isChecked()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nightTheme.setOnClickListener {
            setColorTheme(AppCompatDelegate.MODE_NIGHT_YES)
            isChecked()
        }
        binding.dayTheme.setOnClickListener {
            setColorTheme(AppCompatDelegate.MODE_NIGHT_NO)
            isChecked()
        }

        binding.choseSystemTheme.setOnClickListener {
            setColorTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            isChecked()
        }


    }


    private fun isChecked() {
        val uiManager = requireContext().getSystemService(UI_MODE_SERVICE) as UiModeManager
        var currentMode = uiManager.nightMode
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> {
                binding.nightTheme.isCheckable = true
                binding.nightTheme.isChecked = true
                if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    binding.choseSystemTheme.isChecked = false
                }
            }

            AppCompatDelegate.MODE_NIGHT_NO -> {
                binding.dayTheme.isCheckable = true
                binding.dayTheme.isChecked = true
                if (currentMode == UiModeManager.MODE_NIGHT_NO) {
                    binding.choseSystemTheme.isChecked = false
                }
            }

            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                binding.choseSystemTheme.isCheckable = true
                binding.choseSystemTheme.isChecked = true
                when (currentMode) {
                    AppCompatDelegate.MODE_NIGHT_NO -> binding.dayTheme.isChecked = false
                    AppCompatDelegate.MODE_NIGHT_YES -> binding.nightTheme.isChecked = false

                }
            }
        }
    }


    private fun setColorTheme(colorTheme: Int) {
        settingsViewModel.saveIsNightModeStatus(colorTheme)
        AppCompatDelegate.setDefaultNightMode(colorTheme)
    }


}
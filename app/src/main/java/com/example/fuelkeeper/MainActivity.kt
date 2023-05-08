package com.example.fuelkeeper

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.data.repository.SettingsRepositoryImpl
import com.example.fuelkeeper.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by lazy { viewModels<MainViewModel>().value }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            mainViewModel.isNightMode.collectLatest { isNightMode ->
                when (isNightMode) {
                    MODE_NIGHT_YES -> setTheme(R.style.Theme_FuelKeeperDark)
                    MODE_NIGHT_NO -> setTheme(R.style.Theme_FuelKeeperLight)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        navController = navHostFragment!!.findNavController()
        binding.bottomBavMenu.setOnItemSelectedListener { it: MenuItem ->
            when (it.itemId) {
                R.id.btt_register -> {
                    navController.navigate(R.id.refuelRegisterFragment)
                    true
                }

                R.id.btt_home_stat -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.btt_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }

                else -> {
                    false
                }
            }

        }
    }


}
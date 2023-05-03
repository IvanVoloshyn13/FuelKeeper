package com.example.fuelkeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                else -> {
                    false
                }
            }

        }
    }

}
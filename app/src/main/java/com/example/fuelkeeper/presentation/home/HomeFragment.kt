package com.example.fuelkeeper.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeVieModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bttAddNewRefill.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNewRefuelFragment)
        }
        lifecycleScope.launchWhenStarted {
            homeVieModel.summaryRefuelDetailStateFlow.collectLatest { sumRefuelLog ->
                binding.apply {
                    tvSummaryDistance.text = sumRefuelLog.summaryDistance.toString()
                    tvSummaryPayments.text = sumRefuelLog.summaryPayments.toString()
                    tvSummaryFuel.text = sumRefuelLog.summaryFuel.toString()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            homeVieModel.lastRefuelStateFlow.collectLatest { lastRefuel ->
                binding.apply {
                    tvLastRefuelDistance.text = "${lastRefuel.lastRefuelDistance} km"
                    tvLastRefuelFuelAverage.text = "${lastRefuel.lastRefuelFuelAverage} l/100 km"
                    tvLastRefuelPayment.text = "${lastRefuel.lastRefuelPayment} pln"
                }
            }
        }

    }
}
package com.example.fuelkeeper.presentation.home

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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
        homeVieModel.fuelAmountLiveData.observe(viewLifecycleOwner) {
            binding.tvSummaryFuel.text = it.toString()
        }
        homeVieModel.fuelPaymentsLiveData.observe(viewLifecycleOwner) {
            binding.tvSummaryPayments.text = it.toString()
        }

        homeVieModel.summaryDistanceLiveData.observe(viewLifecycleOwner) {
            binding.tvSummaryDistance.text = it.toString()
        }
        homeVieModel.lastRefuelLiveData.observe(viewLifecycleOwner) { lastRefuel ->
            binding.apply {
                tvLastRefuelDistance.text = lastRefuel.currentMileage.toString()
                tvLastRefuelFuelAverage.text = lastRefuel.fuelAmount.toString()
                tvLastRefuelPayment.text = lastRefuel.fuelPricePerLiter.toString()
            }
        }
    }
}
package com.example.fuelkeeper.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentHomeBinding
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeVieModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private var lastRefuelData = LastRefuelDetailsModel()
    private lateinit var newRefuel: RefuelingModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bttAddNewRefill.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNewRefuelFragment)
        }

        lifecycleScope.launchWhenStarted {
            homeVieModel.summaryRefuelDetailStateFlow.collectLatest { sumRefuelLogResource ->
                when (sumRefuelLogResource) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        sumRefuelLogResource.data.let { data ->
                            if (data != null)
                                binding.apply {
                                    tvSummaryDistance.text = data.summaryDistance.toString()
                                    tvSummaryPayments.text = data.summaryPayments.toString()
                                    tvSummaryFuel.text = data.summaryFuel.toString()
                                }
                        }
                    }
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            view.context,
                            sumRefuelLogResource.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            homeVieModel.lastRefuelStateFlow.collectLatest { lastRefuelResource ->
                when (lastRefuelResource) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        lastRefuelResource.data.let { data ->
                            if (data != null) {
                                binding.apply {
                                    tvLastRefuelDistance.text = "${data.lastRefuelDistance} km"
                                    tvLastRefuelFuelAverage.text = "${data.lastRefuelFuelAverage} ${getString(R.string.l_100_km)}"
                                    tvLastRefuelPayment.text = "${data.lastRefuelPayment} pln"
                                }
                                lastRefuelData = LastRefuelDetailsModel(
                                    lastRefuelDistance = data.lastRefuelDistance,
                                    lastRefuelPayment = data.lastRefuelPayment,
                                    lastRefuelFuelAverage = data.lastRefuelFuelAverage
                                )
                            }
                            addNewRefuelStat()
                        }
                    }
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(view.context, lastRefuelResource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            homeVieModel.allTimeFuelAverageStateFlow.collectLatest { fuelAverageResource ->
                when (fuelAverageResource) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        fuelAverageResource.data.let { data ->
                            if (data != null) {
                                binding.tvFuelAverageDetail.text = data.toString()
                            }
                        }
                    }
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            view.context,
                            fuelAverageResource.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            homeVieModel.summaryDrivingCostStateFlow.collectLatest { drivingCostResource ->
                when (drivingCostResource) {
                    is Resource.Success -> {
                        drivingCostResource.data.let { data ->
                            binding.tvTotalExpensesDetail.text = data.toString()
                        }
                    }
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            view.context,
                            drivingCostResource.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun addNewRefuelStat() {
        val bundle = arguments
        if (bundle == null) {
            Toast.makeText(this@HomeFragment.requireContext(), "CONFIG", Toast.LENGTH_SHORT)
                .show()
        } else {
            val args = HomeFragmentArgs.fromBundle(bundle)
            if (args.newRefuel != null) {
                args.newRefuel.let { data ->
                    if (data != null) {
                        newRefuel = RefuelingModel(
                            refuelDate = data.refuelDate,
                            currentMileage = data.currentMileage,
                            fuelAmount = data.fuelAmount,
                            fuelPricePerLiter = data.fuelPricePerLiter,
                            fillUp = data.fillUp,
                            notes = data.notes
                        )
                    }
                }
                homeVieModel.addNewRefuelStat(newRefuel = newRefuel, newRefuelStat = lastRefuelData)
            }
            bundle.clear()
        }
    }

}

package com.example.fuelkeeper.presentation.refuelingLog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentRefuelRegisterBinding
import com.example.fuelkeeper.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RefuelRegisterFragment : Fragment(), RefuelLogAdapter.OnItemClickListener {
    private var refuelSharedFlowJob: Job? = null
    val refuelRegViewModel: RefuelRegisterViewModel by viewModels()
    private lateinit var binding: FragmentRefuelRegisterBinding
    private lateinit var adapter: RefuelLogAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRefuelRegisterBinding.inflate(inflater)
        adapter = RefuelLogAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        lifecycleScope.launchWhenStarted {
            refuelRegViewModel.refuelStatStateFlow.collectLatest { refuelLogResource ->
                when (refuelLogResource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        refuelLogResource.data?.let {
                            adapter.submitList(it)
                        }
                    }
                    is Resource.Error -> {}
                }

            }
        }



    }

    private fun initRecyclerView() {
        binding.rvFuelLogRegister.adapter = adapter
        binding.rvFuelLogRegister.layoutManager = LinearLayoutManager(
            this@RefuelRegisterFragment.requireContext(),
            VERTICAL, false
        )
    }

    override fun onItemClick(itemId: Int) {
        val directions =
            RefuelRegisterFragmentDirections.actionRefuelRegisterFragmentToEditRefuelItemFragment(
                itemId
            )
        findNavController().navigate(directions)
    }


}
package com.example.fuelkeeper.presentation.refuelingLog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.fuelkeeper.databinding.FragmentRefuelRegisterBinding
import com.example.fuelkeeper.domain.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RefuelRegisterFragment : Fragment(), RefuelLogAdapter.OnItemClickListener,
    RefuelLogAdapter.OnDeleteBttClickListener {
    private val refuelRegViewModel: RefuelRegisterViewModel by viewModels()
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
        adapter.onDeleteBttClickListener = this
    }

    override fun onItemClick(itemId: Int) {
        val directions =
            RefuelRegisterFragmentDirections.actionRefuelRegisterFragmentToEditRefuelItemFragment(
                itemId
            )
        findNavController().navigate(directions)
    }

    override fun onDeleteClick(itemId: Int) {
        lifecycleScope.launch() {
            withContext(Dispatchers.IO) {
                refuelRegViewModel.saveDeletedRefuel(itemId)
                Log.d("ONDELETE", "saveItem")
            }
            withContext(Dispatchers.IO ) {
                refuelRegViewModel.removeRefuelItem(itemId)
                Log.d("ONDELETE", "deleteItem")
            }

            Snackbar.make(
                this@RefuelRegisterFragment.requireView(),
                "Delete Item?",
                Snackbar.LENGTH_LONG
            )
                .setAction("Cancel") {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            refuelRegViewModel.revertRemoving()
                            Log.d("ONDELETE", "revertItem")
                        }
                        Toast.makeText(
                            this@RefuelRegisterFragment.requireContext(),
                            "Delete cancel",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.show()
        }

    }


}
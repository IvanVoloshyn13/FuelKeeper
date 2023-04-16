package com.example.fuelkeeper.presentation.refuelingLog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentRefuelRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefuelRegisterFragment : Fragment() {

    val refuelRegViewModel: RefuelRegisterViewModel by viewModels()
    private lateinit var binding: FragmentRefuelRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRefuelRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
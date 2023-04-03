package com.example.fuelkeeper.presentation.newRefuel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fuelkeeper.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewRefuelFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_refuel, container, false)
    }

    companion object {

    }
}
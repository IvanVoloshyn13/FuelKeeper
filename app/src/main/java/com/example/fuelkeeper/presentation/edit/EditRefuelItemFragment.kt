package com.example.fuelkeeper.presentation.edit

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentEditRefuelItemBinding
import com.example.fuelkeeper.domain.models.RefuelingModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditRefuelItemFragment : Fragment() {
    private val editRefuelViewModel: EditRefuelViewModel by viewModels()
    private lateinit var binding: FragmentEditRefuelItemBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditRefuelItemBinding.inflate(inflater)
        val bundle = arguments
        val args = bundle?.let { EditRefuelItemFragmentArgs.fromBundle(it) }
        args?.refuelItemId.let { itemId ->
            if (itemId != null)
                editRefuelViewModel.getRefuelById(itemId)
        }
        bundle?.clear()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            editRefuelViewModel.refuelSharedFlow.collectLatest { refuelItemResource ->
                binding.apply {
                    refuelItemResource.data.let { refuelItem ->
                        if (refuelItem != null) {
                        etCurrentMileage.setText(refuelItem.currentMileage.toString())
                            etFuelAmount.setText(refuelItem.fuelAmount.toString())
                            etRefuelDate.setText(refuelItem.refuelDate)
                            etFuelPrice.setText(refuelItem.fuelPricePerLiter.toString())
                            etNotes.setText(refuelItem.notes)
                            checkbox.isChecked = refuelItem.fillUp
                        }
                    }
                }
            }
        }


        fuelAmountFocusListener()
        fuelPriceFocusListener()
        currentMileageFocusListener()

        binding.etRefuelDate.showSoftInputOnFocus = false
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.root.setOnClickListener {
            imm.hideSoftInputFromWindow(
                it.applicationWindowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            binding.currentMileageContainer.helperText = validCurrentMileage()
            binding.fuelPriceContainer.helperText = validFuelPrice()
            binding.fuelAmountContainer.helperText = validFuelAmount()
        }

        binding.bttSave.setOnClickListener {
            if (submitForm()) {
                updateRefuel()
            }
        }

    }

    private fun fuelAmountFocusListener() {
        binding.etFuelAmount.setOnFocusChangeListener { view, focused ->
            if (!focused) {
                binding.fuelAmountContainer.helperText = validFuelAmount()
            }
        }
    }

    private fun validFuelAmount(): String? {
        val fuelAmountText = binding.etFuelAmount.text.toString()
        if (fuelAmountText.isEmpty()) {
            return getString(R.string.empty_field_error)
        }
        return null
    }

    private fun currentMileageFocusListener() {
        binding.etCurrentMileage.setOnFocusChangeListener { view, focused ->
            if (!focused) {
                binding.currentMileageContainer.helperText = validCurrentMileage()
            }
        }
    }

    private fun validCurrentMileage(): String? {
        val currentMileageText = binding.etCurrentMileage.text.toString()
        if (currentMileageText.isEmpty()) {
            return getString(R.string.empty_field_error)
        }
        return null
    }


    private fun fuelPriceFocusListener() {
        binding.etFuelPrice.setOnFocusChangeListener { view, focused ->
            if (!focused) {
                binding.fuelPriceContainer.helperText = validFuelPrice()
            }
        }
    }

    private fun validFuelPrice(): String? {
        val fuelPriceText = binding.etFuelPrice.text.toString()
        if (fuelPriceText.isEmpty()) {
            return getString(R.string.empty_field_error)
        }
        return null
    }


    private fun submitForm(): Boolean {
        binding.fuelAmountContainer.helperText = validFuelAmount()
        binding.currentMileageContainer.helperText = validCurrentMileage()
        binding.fuelPriceContainer.helperText = validFuelPrice()
        val validFuelAmount = binding.fuelAmountContainer.helperText == null
        val validCurrentMileage = binding.currentMileageContainer.helperText == null
        val validFuelPrice = binding.fuelPriceContainer.helperText == null
        if (validFuelAmount && validCurrentMileage && validFuelPrice) {
            return true
        }
        return false
    }

    private fun updateRefuel() {
        val date = binding.etRefuelDate.text.toString()
        val currentMileage = binding.etCurrentMileage.text.toString().toInt()
        val fuelAmount = binding.etFuelAmount.text.toString().toDouble()
        val fuelPricePerLiter = binding.etFuelPrice.text.toString().toDouble()
        val notes: String? = binding.etNotes.text.toString()
        val fillUp = binding.checkbox.isChecked
        val newRefuel = RefuelingModel(
            refuelDate = date,
            currentMileage = currentMileage,
            fuelAmount = fuelAmount,
            fuelPricePerLiter = fuelPricePerLiter,
            notes = notes,
            fillUp = fillUp
        )
        editRefuelViewModel.updateRefuel(newRefuel) { isSuccess: Boolean ->
            when (isSuccess) {
                true -> {
                    Toast.makeText(this.requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editRefuelItemFragment_to_homeFragment)
                }
                else -> {
                    Toast.makeText(this.requireContext(), "Some Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
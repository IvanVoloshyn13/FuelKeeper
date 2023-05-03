package com.example.fuelkeeper.presentation.edit

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentEditRefuelItemBinding
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.utils.ValidateManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class EditRefuelItemFragment : Fragment() {
    private var _refuelId: Int? = null
    private val editRefuelViewModel: EditRefuelViewModel by viewModels()
    private lateinit var binding: FragmentEditRefuelItemBinding
    private lateinit var validateManager: ValidateManager
    private lateinit var datePicker: DatePickerDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditRefuelItemBinding.inflate(inflater)
        validateManager = ValidateManager(this.requireContext())
        datePicker = DatePickerDialog(this.requireContext())
        val bundle = arguments
        val args = bundle?.let { EditRefuelItemFragmentArgs.fromBundle(it) }
        args?.refuelItemId.let { itemId ->
            if (itemId != null) {
                editRefuelViewModel.getRefuelById(itemId)
                _refuelId = itemId
            }
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
            editTextContainersValidate()
        }

        binding.bttSave.setOnClickListener {
            if (submitForm()) {
                updateRefuel()
            } else {
                Toast.makeText(this.context, "Please complete all fields ", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        datePicker.setOnCancelListener { binding.etRefuelDate.clearFocus() }

        binding.etRefuelDate.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                datePicker.show()
                setDateByDatePicker()
                imm.hideSoftInputFromWindow(
                    binding.etRefuelDate.applicationWindowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }


    }

    private fun fuelAmountFocusListener() {
        binding.etFuelAmount.setOnFocusChangeListener { view, focused ->
            if (!focused) {
                binding.fuelAmountContainer.helperText =
                    validateManager.validFields(binding.etFuelAmount.text.toString())
            }
        }
    }

    private fun currentMileageFocusListener() {
        binding.etCurrentMileage.setOnFocusChangeListener { view, focused ->
            if (!focused) {
                binding.currentMileageContainer.helperText =
                    validateManager.validFields(binding.etCurrentMileage.text.toString())
            }
        }
    }

    private fun fuelPriceFocusListener() {
        binding.etFuelPrice.setOnFocusChangeListener { view, focused ->
            if (!focused) {
                binding.fuelPriceContainer.helperText =
                    validateManager.validFields(binding.etFuelPrice.text.toString())
            }
        }
    }

    private fun submitForm(): Boolean {
        editTextContainersValidate()
        val validFuelAmount = binding.fuelAmountContainer.helperText == null
        val validCurrentMileage = binding.currentMileageContainer.helperText == null
        val validFuelPrice = binding.fuelPriceContainer.helperText == null
        if (validFuelAmount && validCurrentMileage && validFuelPrice) {
            return true
        }
        return false
    }

    private fun updateRefuel() {
        val refuelId = _refuelId
        val date = binding.etRefuelDate.text.toString()
        val currentMileage = binding.etCurrentMileage.text.toString().toInt()
        val fuelAmount = binding.etFuelAmount.text.toString().toDouble()
        val fuelPricePerLiter = binding.etFuelPrice.text.toString().toDouble()
        val notes: String? = binding.etNotes.text.toString()
        val fillUp = binding.checkbox.isChecked
        val newRefuel = RefuelingModel(
            id = refuelId,
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

    private fun editTextContainersValidate() {
        binding.fuelAmountContainer.helperText =
            validateManager.validFields(binding.etFuelAmount.text.toString())
        binding.currentMileageContainer.helperText =
            validateManager.validFields(binding.etCurrentMileage.text.toString())
        binding.fuelPriceContainer.helperText =
            validateManager.validFields(binding.etFuelPrice.text.toString())
    }

    private fun setDateByDatePicker() {
        var formattedDate: String
        val locale = Locale.getDefault()
        val calendar = Calendar.getInstance()
        datePicker.setOnDateSetListener { datePicker, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
            formattedDate = dateFormat.format(calendar.time)
            binding.etRefuelDate.setText(formattedDate)
            binding.etRefuelDate.clearFocus()

        }
    }

}
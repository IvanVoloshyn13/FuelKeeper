package com.example.fuelkeeper.presentation.newRefuel

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
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentAddNewRefuelBinding
import com.example.fuelkeeper.utils.ValidateManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AddNewRefuelFragment : Fragment() {
    private lateinit var binding: FragmentAddNewRefuelBinding
    private val addNewRefuelViewModel: AddNewRefuelViewModel by viewModels()
    private lateinit var datePicker: DatePickerDialog
    private lateinit var validateManager: ValidateManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNewRefuelBinding.inflate(inflater, container, false)
        datePicker = DatePickerDialog(requireContext())
        validateManager = ValidateManager(this.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etRefuelDate.showSoftInputOnFocus = false

        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.root.setOnClickListener {
            imm.hideSoftInputFromWindow(
                it.applicationWindowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            editTextContainersValidate()
        }

        datePicker.setOnCancelListener { binding.etRefuelDate.clearFocus() }

        binding.apply {
            val data = addNewRefuelViewModel.setLocaleDate()
            etRefuelDate.setText(data)
            etRefuelDate.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    datePicker.show()
                    setDateByDatePicker()
                    imm.hideSoftInputFromWindow(
                        etRefuelDate.applicationWindowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }
        }

        binding.bttSave.setOnClickListener {
            if (submitForm()) {
                createNewRefuel()
            } else {
                Toast.makeText(this.context, "Please complete all fields ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        fuelAmountFocusListener()
        currentMileageFocusListener()
        fuelPriceFocusListener()
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

    private fun createNewRefuel() {
        val date = binding.etRefuelDate.text.toString()
        val currentMileage = binding.etCurrentMileage.text.toString().toInt()
        val fuelAmount = binding.etFuelAmount.text.toString().toDouble()
        val fuelPricePerLiter = binding.etFuelPrice.text.toString().toDouble()
        val notes: String? = binding.etNotes.text.toString()
        val fillUp = binding.checkbox.isChecked
        var newRefuel = com.example.fuelkeeper.domain.models.RefuelingModel(
            refuelDate = date,
            currentMileage = currentMileage,
            fuelAmount = fuelAmount,
            fuelPricePerLiter = fuelPricePerLiter,
            notes = notes,
            fillUp = fillUp
        )
        addNewRefuelViewModel.addNewRefuel(newRefuel) { isSuccess: Boolean ->
            when (isSuccess) {
                true -> {
                    Toast.makeText(this.requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addNewRefuelFragment_to_homeFragment)
                }

                else -> {
                    Toast.makeText(this.requireContext(), "Some Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fuelAmountFocusListener() {
        binding.etFuelAmount.setOnFocusChangeListener { _, focused ->
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

    private fun editTextContainersValidate() {
        binding.fuelAmountContainer.helperText =
            validateManager.validFields(binding.etFuelAmount.text.toString())
        binding.currentMileageContainer.helperText =
            validateManager.validFields(binding.etCurrentMileage.text.toString())
        binding.fuelPriceContainer.helperText =
            validateManager.validFields(binding.etFuelPrice.text.toString())
    }

}




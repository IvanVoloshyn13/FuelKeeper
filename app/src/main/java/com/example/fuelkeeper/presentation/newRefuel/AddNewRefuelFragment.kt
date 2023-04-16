package com.example.fuelkeeper.presentation.newRefuel

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.FragmentAddNewRefuelBinding
import com.example.fuelkeeper.domain.models.LastRefuelDetailsModel
import com.example.fuelkeeper.domain.models.RefuelingModel
import com.example.fuelkeeper.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.*

private const val LAST_REF_DATA = "LastRefuelData"

@AndroidEntryPoint
class AddNewRefuelFragment : Fragment() {
    private lateinit var binding: FragmentAddNewRefuelBinding
    private val addNewRefuelViewModel: AddNewRefuelViewModel by viewModels()
    private lateinit var datePicker: DatePickerDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNewRefuelBinding.inflate(inflater, container, false)
        datePicker = DatePickerDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fuelAmountFocusListener()
        currentMileageFocusListener()
        fuelPriceFocusListener()
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
        datePicker.setOnCancelListener { binding.etRefuelDate.clearFocus() }

        binding.bttSave.setOnClickListener {
            if (submitForm()) {
                createNewRefuel()
            } else {
                Toast.makeText(this.context, "Please complete all fields ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
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

        }
    }

    private fun createNewRefuel() {
        val date = binding.etRefuelDate.text.toString()
        val currentMileage = binding.etCurrentMileage.text.toString().toInt()
        val fuelAmount = binding.etFuelAmount.text.toString().toDouble()
        val fuelPricePerLiter = binding.etFuelPrice.text.toString().toDouble()
        val notes: String? = binding.etNotes.text.toString()
        val fillUp = binding.checkbox.isChecked
        var newRefuel = RefuelingModel(
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
                    if (newRefuel.currentMileage != 0) {
                        val directions =
                            AddNewRefuelFragmentDirections.actionAddNewRefuelFragmentToHomeFragment(
                                newRefuel
                            )
                        findNavController().navigate(directions)
                    }
                }
                else -> {
                    Toast.makeText(this.requireContext(), "Some Error", Toast.LENGTH_SHORT).show()
                }
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
}




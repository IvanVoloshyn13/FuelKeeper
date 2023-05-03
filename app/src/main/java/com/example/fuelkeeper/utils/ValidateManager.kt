package com.example.fuelkeeper.utils

import android.content.Context
import com.example.fuelkeeper.R

class ValidateManager(val context: Context) {
    fun validFields(fieldData: String): String? {
        fieldData
        return if (fieldData.isEmpty())
            context.getString(R.string.empty_field_error)
        else
            return null
    }
}

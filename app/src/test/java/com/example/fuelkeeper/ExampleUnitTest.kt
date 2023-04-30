package com.example.fuelkeeper

import android.content.Context
import android.view.View
import com.example.fuelkeeper.domain.Resource
import com.example.fuelkeeper.domain.repositoryInterface.AllRefuelLogRepository
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.DeleteRefuelLogUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.GetAllRefuelLogUseCase
import com.example.fuelkeeper.domain.usecase.refuelRegisterLogDetail.ReturnDeletedElementUseCase
import com.example.fuelkeeper.presentation.refuelingLog.RefuelRegisterFragment
import com.example.fuelkeeper.presentation.refuelingLog.RefuelRegisterViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


}
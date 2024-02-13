package com.shegs.mintyncodingtest


import com.shegs.mintyncodingtest.data.network.BinDataModel
import com.shegs.mintyncodingtest.data.repository.BinlistRepository
import com.shegs.mintyncodingtest.presentation.viewmodel.BinDataViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class BinDataViewModelTest {

    private lateinit var binlistRepository: BinlistRepository
    private lateinit var binDataViewModel: BinDataViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        binlistRepository = mock()
        binDataViewModel = BinDataViewModel()
    }

    @Test
    fun `fetchBinInfo should update binDataModel on successful fetch`() = runBlockingTest {
        // Given
        val bin = "123456"
        val mockBinDataModel = BinDataModel(/* your mock data here */)
        whenever(binlistRepository.getBinData(bin)).thenReturn(mockBinDataModel)

        // When
        binDataViewModel.fetchBinInfo(bin)

        // Then
        assertEquals(mockBinDataModel, binDataViewModel.binDataModel)
    }

    @Test
    fun `fetchBinInfo should set isLoading to true while fetching data`() = runBlockingTest {
        // Given
        val bin = "123456"
        whenever(binlistRepository.getBinData(bin)).thenReturn(BinDataModel(/* mock data */))

        // When
        binDataViewModel.fetchBinInfo(bin)

        // Then
        assertEquals(true, binDataViewModel.isLoading)
    }

    @Test
    fun `fetchBinInfo should set isLoading to false after fetching data`() = runBlockingTest {
        // Given
        val bin = "123456"
        whenever(binlistRepository.getBinData(bin)).thenReturn(BinDataModel(/* mock data */))

        // When
        binDataViewModel.fetchBinInfo(bin)

        // Then
        assertEquals(false, binDataViewModel.isLoading)
    }

    @Test
    fun `fetchBinInfo should set binDataModel to null on exception`() = runBlockingTest {
        // Given
        val bin = "invalid_bin"
        whenever(binlistRepository.getBinData(bin)).thenThrow(RuntimeException("Mock error"))

        // When
        binDataViewModel.fetchBinInfo(bin)

        // Then
        assertEquals(null, binDataViewModel.binDataModel)
    }

    // Add more tests for different scenarios

}

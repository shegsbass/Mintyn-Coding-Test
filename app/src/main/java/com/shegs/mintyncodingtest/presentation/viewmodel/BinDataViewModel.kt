package com.shegs.mintyncodingtest.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shegs.mintyncodingtest.data.network.BinDataModel
import com.shegs.mintyncodingtest.data.repository.BinlistRepository
import kotlinx.coroutines.launch

class BinDataViewModel : ViewModel() {
    private val binlistRepository = BinlistRepository()

    // State variables
    private var _binDataModel: BinDataModel? by mutableStateOf(null)
    val binDataModel: BinDataModel? get() = _binDataModel

    private var _isLoading by mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading


    // Function to fetch BIN information
    fun fetchBinInfo(bin: String) {
        viewModelScope.launch {
            try {
                _isLoading = true
                _binDataModel = binlistRepository.getBinData(bin)
            } catch (e: Exception) {
                _binDataModel = null
            }
            finally {
                _isLoading = false
            }
        }
    }
}
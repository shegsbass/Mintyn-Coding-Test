package com.shegs.mintyncodingtest.data.repository

import com.shegs.mintyncodingtest.data.network.RetrofitInstance
import com.shegs.mintyncodingtest.data.network.BinDataModel

class BinlistRepository {
    suspend fun getBinData(bin: String): BinDataModel {
        // Make the network request here using Retrofit or your preferred networking library
        return RetrofitInstance.binlistAPI.getBinData(bin)
    }
}
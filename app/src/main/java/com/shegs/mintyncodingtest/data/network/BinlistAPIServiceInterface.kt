package com.shegs.mintyncodingtest.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface BinlistAPIServiceInterface {

    @GET("{bin}")
    suspend fun getBinData(@Path("bin") bin: String): BinDataModel

}
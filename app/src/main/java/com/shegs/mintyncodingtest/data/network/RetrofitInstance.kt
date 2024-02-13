package com.shegs.mintyncodingtest.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://lookup.binlist.net/"

    val binlistAPI: BinlistAPIServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinlistAPIServiceInterface::class.java)
    }
}
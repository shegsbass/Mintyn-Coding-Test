package com.shegs.mintyncodingtest.data.network

import kotlinx.serialization.Serializable

@Serializable
data class BinDataModel (
    val number: Number? = null,
    val scheme: String? = null,
    val type: String? = null,
    val brand: String? = null,
    val prepaid: Boolean? = null,
    val country: Country? = null,
    val bank: Bank? = null
)

@Serializable
data class Bank (
    val name: String? = null
)

@Serializable
data class Country (
    val numeric: String? = null,
    val alpha2: String? = null,
    val name: String? = null,
    val emoji: String? = null,
    val currency: String? = null,
    val latitude: Long? = null,
    val longitude: Long? = null
)

@Serializable
class Number()

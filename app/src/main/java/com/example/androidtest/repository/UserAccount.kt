package com.example.androidtest.repository

import android.os.Bundle
import com.google.gson.annotations.SerializedName

const val CREDENTIAL_TYPE: String = "com.example.androidtest"

data class UserAccount(
    var userId: Int,
    var name: String,
    var bankAccount: String,
    var agency: String,
    var balance: Double
) {

    constructor(raw: AccountRaw) : this(
        userId = raw.userId!!,
        name = raw.name!!,
        bankAccount = raw.bankAccount!!,
        agency = raw.agency!!,
        balance = raw.balance!!
    )

    fun credentialBundle(): Bundle = Bundle().apply {
        putString("userId", userId.toString())
        putString("name", name)
        putString("bankAccount", bankAccount)
        putString("agency", agency)
        putString("balance", balance.toString())
    }

}

data class AccountRaw(
    @SerializedName("agency") val agency: String?,
    @SerializedName("balance") val balance: Double?,
    @SerializedName("bankAccount") val bankAccount: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("userId") val userId: Int?
)






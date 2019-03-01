package com.example.androidtest.repository

import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.SuppressLint
import com.example.androidtest.api.ServiceManager
import com.example.androidtest.api.ServiceResponseException
import com.example.androidtest.api.serviceCall
import java.text.ParseException


object Repository {

    lateinit var accountManager: AccountManager

    fun loginCall(user: String, pass: String, callback: (apiResponse: ApiResponse) -> Unit) {

        ServiceManager.getApi().postLogin(user, pass).serviceCall(
            {
                checkinAccount(user, pass, UserAccount(it.userAccount))
                callback(SuccessResponse())
            },
            {
                when (it) {
                    is ServiceResponseException,
                    is NullPointerException,
                    is ParseException -> callback(FailureResponse("Erro ao processar a resposta do Servidor."))

                    else -> callback(FailureResponse("Não foi possível realizar o Login."))
                }
            }
        )
    }


    fun getRecentStatements(callback: (ApiResponse, ArrayList<Statement>) -> Unit) {
        val userAccount = getLoggedAccount()!!

        ServiceManager.getApi().getStatements(userAccount.userId).serviceCall(
            {

            }, {

            }
        )
    }

    fun logoff() {
        checkoutAccount()
        // TODO: When logged out, navigate to LoginActivity
//        goToLoginActivity()
    }

    private fun checkinAccount(user: String, password: String, userAccount: UserAccount) {
        accountManager.addAccountExplicitly(
            Account(user, CREDENTIAL_TYPE),
            password,
            userAccount.credentialBundle()
        )
    }


    private fun getAllCredentials() = accountManager.getAccountsByType(CREDENTIAL_TYPE)

    fun getLoggedAccount(): UserAccount? {

        val credentials = getAllCredentials()

        credentials.forEach {
            val userAccount = UserAccount(
                userId = accountManager.getUserData(it, "userId ").toInt(),
                name = accountManager.getUserData(it, "name "),
                bankAccount = accountManager.getUserData(it, "bankAccount "),
                agency = accountManager.getUserData(it, "agency "),
                balance = accountManager.getUserData(it, "balance ").toDouble()
            )
            return userAccount
        }

        return null
    }


    // TODO: Wipe app Credentials
    private fun checkoutAccount() {

    }

}



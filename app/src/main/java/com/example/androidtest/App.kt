package com.example.androidtest

import android.accounts.AccountManager
import android.app.Application
import com.example.androidtest.repository.Repository

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        Repository.accountManager = AccountManager.get(this)
    }
}
package com.test.user.tavorapplication

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

class TavorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setStetho()
    }

    private fun setStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }
    }
}
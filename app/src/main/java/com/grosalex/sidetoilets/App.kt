package com.grosalex.sidetoilets

import android.app.Application
import com.grosalex.sidetoilets.api.RatpService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    lateinit var service: RatpService
    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl(RatpService.ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create<RatpService>(RatpService::class.java)
        app = this
    }

    companion object {
        lateinit var app: App

        fun get(): App = app
    }
}
package com.grosalex.sidetoilets.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.grosalex.sidetoilets.Navigator
import com.grosalex.sidetoilets.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            Navigator.startMapActivity(this)
        }, 2000)
    }
}
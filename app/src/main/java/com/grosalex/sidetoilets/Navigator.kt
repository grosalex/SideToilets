package com.grosalex.sidetoilets

import android.content.Context
import android.content.Intent
import com.grosalex.sidetoilets.activity.MapsActivity

object Navigator {
    fun startMapActivity(context: Context){
        val intent = Intent(context, MapsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
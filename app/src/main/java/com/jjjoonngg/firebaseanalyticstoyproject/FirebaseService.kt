package com.jjjoonngg.firebaseanalyticstoyproject

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class FirebaseService : Service() {

    private var flag = false
    private lateinit var timerTask : Timer

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    override fun onCreate() {
        super.onCreate()
        var i = 0
        timerTask = timer(period = 100) {
            Log.d("TAG", "" + i++)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerTask.cancel()
    }

}

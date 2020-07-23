package com.jjjoonngg.firebaseanalyticstoyproject

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class FirebaseService : Service() {

    private var flag = false
    private lateinit var timerForLogin: Timer
    private lateinit var timerForSignUp: Timer
    private lateinit var timerForTutorial: Timer
    private lateinit var context: Context

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }


    override fun onCreate() {
        super.onCreate()
        context = this
        //1second * seconds * minutes * hours
        timerForTutorial = timer(period = Constant.MILLSTIME * 60 * 60 * 1L) {
            FirebaseAnalyticsManager(Constant.TYPE_TUTORIAL, context)
        }
        timerForLogin = timer(period = Constant.MILLSTIME * 60 * 60 * 2L) {
            FirebaseAnalyticsManager(Constant.TYPE_LOGIN, context)
        }
        timerForSignUp = timer(period = Constant.MILLSTIME * 60 * 60 * 3L) {
            FirebaseAnalyticsManager(Constant.TYPE_SIGN_UP, context)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerForLogin.cancel()
        timerForSignUp.cancel()
        timerForTutorial.cancel()
    }

}

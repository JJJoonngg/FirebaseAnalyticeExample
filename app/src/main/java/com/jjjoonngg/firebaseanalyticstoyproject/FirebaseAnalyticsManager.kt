package com.jjjoonngg.firebaseanalyticstoyproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsManager(private val type: Int, private val context: Context) {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var bundle: Bundle

    init {
        if (type == Constant.DEFAULT) {
            Log.d("TAG", "startAnalytics: Error !!")
        } else {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            bundle = Bundle()
            sendInformation()
        }
    }

    private fun sendInformation() {
        bundle.clear()
        when (type) {
            Constant.TYPE_LOGIN -> {
                bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, "Login")
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
            }
            Constant.TYPE_TUTORIAL -> {
                bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, "Tutorial Begin")
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_BEGIN, bundle)
                bundle.clear()
                bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, "Tutorial Complete")
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_COMPLETE, bundle)
            }
            Constant.TYPE_SIGN_UP -> {
                bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, "Sign Up")
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)
            }
        }
    }
}
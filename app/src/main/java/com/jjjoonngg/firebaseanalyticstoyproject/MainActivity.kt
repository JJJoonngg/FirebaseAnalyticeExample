package com.jjjoonngg.firebaseanalyticstoyproject

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var context: Context
    private lateinit var bundle: Bundle
    private lateinit var firebaseAnalyticsManager: FirebaseAnalyticsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        context = applicationContext
        bundle = Bundle()

        button_start.setOnClickListener(this)
        button_stop.setOnClickListener(this)
        button_end.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_start -> {
                if (isServiceRunning())
                    Toast.makeText(this, "Already Running Service", Toast.LENGTH_SHORT).show()
                else {
                    startService(Intent(this, FirebaseService::class.java))
                    text_status.setText("Program Running")
                }
            }
            R.id.button_stop -> {
                stopService(Intent(this, FirebaseService::class.java))
                text_status.setText("Program Stop \nSo Restart Plz")
            }
            R.id.button_end -> {
                exitProcess(1)
            }
        }
    }

    private fun isServiceRunning(): Boolean {
        val activityManager: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (FirebaseService::class.java.name == service.service.className)
                return true
        }
        return false
    }
}
package com.example.useractivity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var handler: Handler? = null
    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L
    var prefs: SharedPreferences? = null
    internal var millsec:Long=0
    internal var Seconds: Int = 0
    internal var Minutes: Int = 0
    internal var MilliSeconds: Int = 0
    private val sharedPrefFile = "kotlinsharedpreference"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        handler = Handler()

        StartTime =SystemClock.uptimeMillis()
        millsec = prefs!!.getLong("millsec",0)
        Log.e("millsec1", millsec.toString())
        handler?.postDelayed(runnable, 0)




    }





    // Method to get days hours minutes seconds from milliseconds
    private fun timeString(millisUntilFinished:Long):String{
        var millisUntilFinished:Long = millisUntilFinished
        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        millisUntilFinished -= TimeUnit.DAYS.toMillis(days)

        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

        Log.e("millsec2",millisUntilFinished.toString())


        val editor = prefs!!.edit()
        Log.e("millsec", millisUntilFinished.toString())
        editor.putLong("millsec",millisUntilFinished)
        editor.apply()
        editor.commit()

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d D: %02d h: %02d m: %02d s",
            days,hours, minutes,seconds
        )
    }


    var runnable: Runnable = object : Runnable {

        override fun run() {

            if (!millsec.equals(0)) {
                MillisecondTime = SystemClock.uptimeMillis() - (StartTime-millsec)
            }else {
                MillisecondTime = SystemClock.uptimeMillis() - StartTime
            }

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 1000).toInt()



            val timeRemaining = timeString(MillisecondTime)
            txt_timer.text = timeRemaining

            handler?.postDelayed(this, 0)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
    }
}




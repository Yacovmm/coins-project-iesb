package com.example.iesbcoinapp.infra

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.*
import java.util.Calendar.HOUR_OF_DAY

typealias HourAndMinute = Pair<Int, Int>

object AlarmManagerHelper {

    lateinit var sharedPreference: SharedPreferences

    fun setAlarm(context: Context, time: HourAndMinute) {
        val calendar = Calendar.getInstance().apply {
            set(HOUR_OF_DAY, time.first)
            set(Calendar.MINUTE, time.second)
            set(Calendar.SECOND, 0)
        }

        println(calendar.time)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent = getPendingIntent(context)

        // Other possible functions: setExact() / setRepeating() / setWindow(), etc
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        saveAlarm(context, time.first, time.second)
    }

    fun retrieveHourAndMinute(context: Context): HourAndMinute {
        sharedPreference = getSharedPreference(context)

        val hour = sharedPreference.getInt("hour", 0)
        val minutes = sharedPreference.getInt("minutes", 0)
        return HourAndMinute(hour, minutes)
    }

    private fun saveAlarm(context: Context, hour: Int, minutes: Int) {
        sharedPreference = getSharedPreference(context)

        sharedPreference.edit {
            putInt("hour", hour)
            putInt("minutes", minutes)
            commit()
        }
    }

    private fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("alarmref", Context.MODE_PRIVATE)
    }

    private fun getPendingIntent(context: Context): PendingIntent {

        val intent = Intent(context, MyBroadCastReceiver::class.java).apply {
            action = "com.iesbcoinapp.alarmmanager"
        }

        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}

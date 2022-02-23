package com.example.iesbcoinapp.infra

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            "com.iesbcoinapp.alarmmanager" -> {
                Toast.makeText(context, "Alarm time", Toast.LENGTH_SHORT).show()
                println("Teste")
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                context?.let {
                    val alarmTime = AlarmManagerHelper.retrieveHourAndMinute(context)
                    AlarmManagerHelper.setAlarm(context, alarmTime)
                }
            }
            else -> return
        }
    }
}

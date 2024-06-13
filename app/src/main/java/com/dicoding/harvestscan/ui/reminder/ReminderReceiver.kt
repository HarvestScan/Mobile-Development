package com.dicoding.harvestscan.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dicoding.harvestscan.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val plantName = intent.getStringExtra("plantName")
        val notes = intent.getStringExtra("notes")

        val notification = NotificationCompat.Builder(context, "REMINDER_CHANNEL")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Pengingat Tanaman")
            .setContentText("Ingat untuk merawat tanaman: $plantName. $notes")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(0, notification)
    }
}

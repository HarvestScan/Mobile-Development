package com.dicoding.harvestscan.ui.menumyplant.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dicoding.harvestscan.MainActivity
import com.dicoding.harvestscan.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ReminderReceiver", "Reminder received")

        val plantName = intent.getStringExtra(context.getString(R.string.plantname))
        val notes = intent.getStringExtra(context.getString(R.string.notes))

        Log.d("ReminderReceiver", "Plant name: $plantName, Notes: $notes")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = System.currentTimeMillis().toInt()
        val channelId = context.getString(R.string.reminder_channel_id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                context.getString(R.string.reminder_channel),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.channel_for_reminder_notifications)
                enableLights(true)
                lightColor = Color.RED
            }
            notificationManager.createNotificationChannel(channel)
        }

        val mainIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.apk_logo)
            .setContentTitle(context.getString(R.string.reminder_for_plant))
            .setContentText(
                context.getString(
                    R.string.don_t_forget_to_take_care_of_the_plants_with_notes,
                    plantName,
                    notes
                ))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}


package com.dicoding.harvestscan.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dicoding.harvestscan.R

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val plantName = inputData.getString("plant_name")
        val notes = inputData.getString("notes")

        showNotification(plantName, notes)

        return Result.success()
    }

    private fun showNotification(plantName: String?, notes: String?) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("REMINDER_CHANNEL", "Reminder", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "REMINDER_CHANNEL")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Pengingat Tanaman")
            .setContentText("Ingat untuk merawat tanaman: $plantName. $notes")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(0, notification)
    }
}

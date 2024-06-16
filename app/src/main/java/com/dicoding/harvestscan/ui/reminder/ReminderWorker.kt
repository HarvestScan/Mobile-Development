package com.dicoding.harvestscan.ui.reminder

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
        val plantName = inputData.getString(applicationContext.getString(R.string.plantname))
        val notes = inputData.getString(applicationContext.getString(R.string.notes))

        showNotification(plantName, notes)

        return Result.success()
    }

    private fun showNotification(plantName: String?, notes: String?) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(applicationContext.getString(R.string.reminder_channel_uppercase),
                applicationContext.getString(
                    R.string.reminder
                ), NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.reminder_channel_uppercase))
            .setSmallIcon(R.drawable.apk_logo)
            .setContentTitle(applicationContext.getString(R.string.reminder_for_plant))
            .setContentText(applicationContext.getString(R.string.don_t_forget_to_take_care_of_the_plants_with_notes))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(0, notification)
    }
}

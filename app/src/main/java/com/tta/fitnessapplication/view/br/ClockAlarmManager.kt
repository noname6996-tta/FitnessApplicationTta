package com.tta.fitnessapplication.view.br

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tta.fitnessapplication.data.model.noti.Notification

const val CHANNEL_ID_NOTI: String = "MyNotificationChannel"
class ClockAlarmManager : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context,intent)
    }

    private fun showNotification(context: Context, intent: Intent){
        val notiInfo = intent?.getSerializableExtra("noti_info") as? Notification
        val intent1 = Intent(context, NewOnCompletedBroadcastReceiver::class.java).apply {
            putExtra("noti_info", notiInfo)
        }
        val pendingIntent1: PendingIntent? =
            notiInfo?.let {
                PendingIntent.getBroadcast(
                    context,
                    notiInfo.id,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
        val action1: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Completed", pendingIntent1).build()

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_NOTI)
            .setSmallIcon(notiInfo!!.icon)
            .setContentTitle(notiInfo.title)
            .setContentText(notiInfo.text)
            .addAction(action1)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


//        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Check if the notification channel needs to be created (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        // Show the notification
//        val notificationManagerCompat = NotificationManagerCompat.from(context)
//        notificationBuilder?.let {
//            notiInfo?.let { it1 -> notificationManagerCompat?.notify(it1.id, it) }
//        }
        notificationManager.notify(notiInfo.id, notificationBuilder.build())
    }

    private fun createNotificationChannel(notificationManager: NotificationManager){
        // Create a unique notification channel if running on Android Oreo (API 26) or higher
        val channelName = "Alarm Reminder Channel"
        val notificationChannel = NotificationChannel(
            CHANNEL_ID_NOTI,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
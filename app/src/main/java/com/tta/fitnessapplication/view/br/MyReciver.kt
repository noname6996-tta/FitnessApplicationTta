package com.tta.fitnessapplication.view.br

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.tta.fitnessapplication.R
import java.util.*

// Define a unique identifier for the notification channel
const val CHANNEL_ID: String = "MyNotificationChannel"

// Define the notification ids for the individual alarms
const val NOTIFICATION_ID_1 = 1
const val NOTIFICATION_ID_2 = 2
const val NOTIFICATION_ID_3 = 3

// Create a BroadcastReceiver to handle the alarm triggers
class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("NOTIFICATION_ID", 0)
        val message = intent.getStringExtra("MESSAGE")

        // Display the notification
        showNotification(context, notificationId, message)
    }

    private fun showNotification(context: Context, notificationId: Int, message: String?) {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_bed)
            .setContentTitle("Alarm Notification")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if the notification channel needs to be created (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        // Show the notification
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "Alarm Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }
}

// Schedule the alarms
fun scheduleNotifications(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val calendar = Calendar.getInstance()

    // Set the first alarm time
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 8)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val intent1 = Intent(context, MyReceiver::class.java).apply {
        putExtra("NOTIFICATION_ID", NOTIFICATION_ID_1) // Unique id for the notification
        putExtra("MESSAGE", "This is notification 1")
    }
    val pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT)

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent1
    )

    // Set the second alarm time
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 12)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val intent2 = Intent(context, MyReceiver::class.java).apply {
        putExtra("NOTIFICATION_ID", NOTIFICATION_ID_2) // Unique id for the notification
        putExtra("MESSAGE", "This is notification 2")
    }
    val pendingIntent2 = PendingIntent.getBroadcast(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT)

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent2
    )

    // Set the third alarm time
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 18)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val intent3 = Intent(context, MyReceiver::class.java).apply {
        putExtra("NOTIFICATION_ID", NOTIFICATION_ID_3) // Unique id for the notification
        putExtra("MESSAGE", "This is notification 3")
    }
    val pendingIntent3 = PendingIntent.getBroadcast(context, 2, intent3, PendingIntent.FLAG_UPDATE_CURRENT)

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent3
    )
}

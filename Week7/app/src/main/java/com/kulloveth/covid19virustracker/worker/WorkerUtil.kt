package com.kulloveth.covid19virustracker.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.ui.MainActivity

const val NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL"
private const val NOTIFICATION_CHANNEL_DESCRIPTION = "DATA_UPDATED"
private const val CHANNEL_ID = "CHANNEL_ID"
private const val NOTIFICATION_ID = 222
private const val NOTIFICATION_TITLE = "COVID INFO UPDATE"

/**
 * Create a Notification that indicates when data is syncronized
 *
 * @param message Message shown on the notification
 */
fun makeStatusNotification(message: String) {
    val context = App.getContext()
    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = NOTIFICATION_CHANNEL_NAME
        val description = NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val notifyIntent = Intent(App.getContext(), MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    //an intent to lead user back into the app from the notification
    val notifyPendingIntent = PendingIntent.getActivity(
        App.getContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
        setContentIntent(notifyPendingIntent)
        setSmallIcon(R.drawable.covid)
        setContentTitle(NOTIFICATION_TITLE)
        setContentText(message)
        priority = NotificationCompat.PRIORITY_HIGH
        setVibrate(LongArray(0))
    }

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}


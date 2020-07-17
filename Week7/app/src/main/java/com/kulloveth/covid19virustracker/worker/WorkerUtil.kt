package com.kulloveth.covid19virustracker.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.R

const val STATUS_NOTIFICATION_CHANNEL_NAME = "STATUS"
private const val STATUS_NOTIFICATION_CHANNEL_DESCRIPTION = "STATUS_DATA_UPDATED"
private const val STATUS_CHANNEL_ID = "STATUS_ID"
private const val STATUS_NOTIFICATION_ID = 222
private const val STATUS_NOTIFICATION_TITLE = "STATUS UPDATE"

/**
 * Create a Notification that is shown as a heads-up notification if possible.
 *
 * @param message Message shown on the notification
 */
fun makeStatusNotification(message: String) {
    val context = App.getContext()
    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = STATUS_NOTIFICATION_CHANNEL_NAME
        val description = STATUS_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(STATUS_CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, STATUS_CHANNEL_ID)
        .setSmallIcon(R.drawable.covid)
        .setContentTitle(STATUS_NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(STATUS_NOTIFICATION_ID, builder.build())
}


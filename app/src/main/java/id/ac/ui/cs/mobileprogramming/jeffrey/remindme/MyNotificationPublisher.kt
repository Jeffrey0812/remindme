package id.ac.ui.cs.mobileprogramming.jeffrey.remindme

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.NOTIFICATION
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.NOTIFICATION_CHANNEL_ID
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.NOTIFICATION_ID

class MyNotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        assert(notificationManager != null)
        notificationManager.notify(id, notification)
    }




}
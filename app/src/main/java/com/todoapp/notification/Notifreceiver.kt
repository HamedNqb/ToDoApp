package com.todoapp.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.todoapp.R
import com.todoapp.fragment.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class Notifreceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingIntent = NavDeepLinkBuilder(context!!).setGraph(R.navigation.nav_graph)
            .setDestination(R.id.currentToDos).createPendingIntent()
        val currentId = intent!!.extras!!.getString("id")
        val notifManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        runBlocking {
            val currentTodo =
                context.dataStore.data.first().toDoList.find { it.hashCode() == currentId?.toInt() }
            creatNotification(
                context,
                currentId.toString(),
                currentTodo!!.title,
                currentTodo.description,
                notifManager, pendingIntent
            )
        }
    }

    private fun creatNotification(
        context: Context?,
        id: String,
        title: String,
        desc: String,
        notificationManager: NotificationManager,
        pendingIntent: PendingIntent
    ) {
        val notification = NotificationCompat.Builder(context!!, id)
            .setSmallIcon(R.drawable.baseline_circle_notifications).setContentTitle(title)
            .setContentText(desc).setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent).build()
        notificationManager.notify(id.toInt(), notification)
    }
}
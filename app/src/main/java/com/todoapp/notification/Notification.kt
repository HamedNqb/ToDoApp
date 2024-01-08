package com.todoapp.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.todoapp.model.ToDo
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class Notification(
    var context: Context,
    var id: String,
    var todo: ToDo,
    var name: String,
    var description: String
) {
    init {
        createChannel(name, description)
        startNotification()
    }
    @SuppressLint("ScheduleExactAlarm")
    private fun startNotification() {
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY, (todo.time).split(":")[0].toInt())
        calender.set(Calendar.MINUTE, (todo.time).split(":")[1].toInt())
        calender.set(Calendar.SECOND, 0)
        calender.set(Calendar.MILLISECOND, 0)
        calender.set(Calendar.YEAR, (todo.date).split(":")[2].toInt())
        calender.set(Calendar.MONTH, (todo.date).split(":")[1].toInt()-1)
        calender.set(Calendar.DAY_OF_YEAR, (todo.date).split(":")[0].toInt())
        val notifIntent = Intent(context, Notifreceiver::class.java)
        notifIntent.putExtra("id", id)
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            PendingIntent.getBroadcast(
                context,
                id.toInt(),
                notifIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(name: String, description: String) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)
        channel.description = description
        val notifManager = context.getSystemService(NotificationManager::class.java)
        notifManager.createNotificationChannel(channel)
    }
}
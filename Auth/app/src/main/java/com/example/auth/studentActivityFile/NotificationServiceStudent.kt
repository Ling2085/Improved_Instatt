package com.example.auth.studentActivityFile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.auth.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NotificationServiceStudent: Service() {
    private lateinit var database : DatabaseReference
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //When this service is start, run the function inside
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val studentName = intent?.getStringExtra("Name").toString()
        Log.i("MyTag", "notification")
        Log.i("MyTag", studentName)
        startForegroundServiceWithNotification()
        listenForAbsentFormStatus(studentName)

        return START_STICKY
    }

    //Check the Firebase data
    //If there the absent form is reviewed by lecturer, a notification will be sent to notify student
    private fun listenForAbsentFormStatus(studentName: String) {
        database = FirebaseDatabase.getInstance().getReference("AbsentRequest").child(studentName)
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                if (dataSnapshot.exists()) {
                    val moduleName = dataSnapshot.key.toString()
                    val approved = dataSnapshot.value.toString()
                    Log.i("MyTag",approved)
                    sendLocalNotification(moduleName,approved)
                    database.removeValue()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //Send Notification for the status of absent form
    private fun sendLocalNotification(moduleName: String, approved: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "absent_form_channel"

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Absent Form Request")
            .setContentText("Your absent form for $moduleName is $approved")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2, notification)
    }

    //Start foreground service to ensure that the notification still can be sent if the app is not open
    private fun startForegroundServiceWithNotification() {
        val channelId = "absent_form_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Absent Form Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Service Running")
            .setContentText("Listening for absent forms...")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .build()

        startForeground(1, notification)
    }

}
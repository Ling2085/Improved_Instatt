package com.example.auth.lecturerActivityFile

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

class NotificationService: Service() {
    private lateinit var database :DatabaseReference
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //When this service is start, run the function inside
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val lecturerName = intent?.getStringExtra("Name").toString()
        Log.i("MyTag", "notification")
        Log.i("MyTag", lecturerName)
        startForegroundServiceWithNotification()
        listenForNewAbsentForm(lecturerName)

        return START_STICKY
    }

    //Send Notification for new absent form that need to be reviewed
    private fun sendLocalNotification(lecturerName: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "absent_form_channel"

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("New Notification")
            .setContentText("New Absent Form need to be reviewed")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Set priority
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2, notification) // Pass a unique ID for each notification
    }

    //Check the Firebase data
    //If there is new absent form, a notification will be sent to notify lecturer
    private fun listenForNewAbsentForm(lecturerName: String) {
        database = FirebaseDatabase.getInstance().getReference("AbsentForm").child(lecturerName)
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                if (dataSnapshot.exists()) {
                    sendLocalNotification(lecturerName)
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

    //Start foreground service to ensure that the notification still can be sent if the app is not open
    private fun startForegroundServiceWithNotification() {
        val channelId = "absent_form_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android 8.0+
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
            .setSmallIcon(R.drawable.baseline_notifications_24) // Replace with your icon
            .build()

        startForeground(1, notification) // Pass a unique ID and the notification
    }


}

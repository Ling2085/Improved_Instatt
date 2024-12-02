package com.example.auth.lecturerActivityFile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.auth.LecturerHistory
import com.example.auth.R
import com.example.auth.StudentHistory
import com.example.auth.databinding.ActivityAbsentFormHistoryLecturerBinding
import com.example.auth.studentActivityFile.AbsentFormHistoryStudentAdapter
import com.example.auth.studentActivityFile.StudentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AbsentFormHistoryLecturerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbsentFormHistoryLecturerBinding
    private lateinit var database: DatabaseReference
    private lateinit var username :String
    private lateinit var historyLecturer : ArrayList<LecturerHistory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAbsentFormHistoryLecturerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get username from shared preferences
        username = getUsername(this).toString()
        historyLecturer = arrayListOf()

        //handle back navigation
        binding.iBback.setOnClickListener{
            val intent = Intent(this, LecturerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("Name", username)
            startActivity(intent)
        }
        //--------------------------------------------------------------------------------------//

        //Get History Data From Firebase Database
        database = FirebaseDatabase.getInstance().getReference("Absent Form History Lecturer").child(username)
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    historyLecturer.clear()
                    Log.i("MyTag","Test 1")
                    for (i in snapshot.children) {
                        val user = i.getValue(LecturerHistory::class.java)
                        historyLecturer.add(user!!)
                    }
                }
                val adapter = AbsentFormHistoryLecturerAdapter(historyLecturer)
                binding.rvHistoryLecturer.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.rvHistoryLecturer.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }


    }

    //This function is to get username from shared preferences
    private fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }
}
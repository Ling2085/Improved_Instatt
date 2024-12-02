package com.example.auth.studentActivityFile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.auth.StudentHistory
import com.example.auth.databinding.ActivityAbsentFormHistoryStudentBinding
import com.example.auth.lecturerActivityFile.LecturerActivity
import com.example.auth.signInActivityFile.SignInViewModel
import com.example.auth.signInActivityFile.SignInViewModelFactory
import com.example.auth.signInActivityFile.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AbsentFormHistoryStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbsentFormHistoryStudentBinding
    private lateinit var database: DatabaseReference
    private lateinit var username :String
    private lateinit var historyStudent : ArrayList<StudentHistory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAbsentFormHistoryStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get username from shared preferences
        username = getUsername(this).toString()
        Log.i("MyTag",username)
        //------------------------------------------------------------//


        historyStudent = arrayListOf()

        //handle back navigation
        binding.iBback.setOnClickListener{
            val intent = Intent(this, StudentActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("Name", username)
            startActivity(intent)
        }
        //--------------------------------------------------------------------------------------//

        //Get the Absent Form History data from Firebase Database
        database = FirebaseDatabase.getInstance().getReference("Absent Form History Student").child(username)
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    historyStudent.clear()
                    Log.i("MyTag","Test 1")
                    for (i in snapshot.children) {
                        val user = i.getValue(StudentHistory::class.java)
                        historyStudent.add(user!!)
                    }
                }
                val adapter = AbsentFormHistoryStudentAdapter(historyStudent)
                binding.rvHistoryStudent.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }



        })


        binding.rvHistoryStudent.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        //----------------------------------------------------------------------------------------------------------//

    }

    //This function is to get username from shared preferences
    private fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }
}
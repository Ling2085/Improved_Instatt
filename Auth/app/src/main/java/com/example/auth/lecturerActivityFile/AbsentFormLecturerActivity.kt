package com.example.auth.lecturerActivityFile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.auth.LecturerHistory
import com.example.auth.StudentHistory
import com.example.auth.databinding.ActivityAbsentFormLecturerBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AbsentFormLecturerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbsentFormLecturerBinding
    private lateinit var databaseAbsentRequest: DatabaseReference
    private lateinit var databaseRemove :DatabaseReference
    private lateinit var databaseSaveHistory :DatabaseReference
    private lateinit var lecturerName :String
    private lateinit var name :String
    private lateinit var id :String
    private lateinit var date :String
    private lateinit var time :String
    private lateinit var moduleName :String
    private lateinit var reason :String
    private lateinit var userName :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAbsentFormLecturerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtain data through intent from Lecturer Fragment 3
        lecturerName = intent.getStringExtra("lecturerName").toString()
        name = intent.getStringExtra("Name").toString() //Lecturer Name
        id = intent.getStringExtra("StudentID").toString()
        date = intent.getStringExtra("Date").toString()
        time = intent.getStringExtra("Time",).toString()
        moduleName = intent.getStringExtra("ModuleName").toString()
        reason = intent.getStringExtra("Reason").toString()
        userName = intent.getStringExtra("userName").toString() //Student Name
        //--------------------------------------------------------------------------------------------//

        Log.i("MyTag", lecturerName!!)
        Log.i("MyTag", userName!!)

        binding.tvName.text = name
        binding.tvStudentID.text = id
        binding.tvDate.text = date
        binding.tvTime.text = time
        binding.tvModule.text = moduleName
        binding.tvReason.text = reason

        //Handle back navigation
        binding.iBback.setOnClickListener{
//            Toast.makeText(this,"Hello U made it",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LecturerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        //-----------------------------------------------------------------------------------------//


        //If the approved button is pressed
        binding.btnApproved.setOnClickListener {
            //Use Firebase to send notification to the student for the status of the absent form which is approved
            databaseAbsentRequest = FirebaseDatabase.getInstance().getReference("AbsentRequest")
            databaseAbsentRequest.child(userName!!).child(moduleName!!).setValue("Approved")
            Log.i("MyTag", "Approved")
            //Delete the Absent Form which is already reviewed
            databaseRemove = FirebaseDatabase.getInstance().getReference(("AbsentForm"))
            databaseRemove.child(lecturerName!!).child(userName).removeValue()
            //Save the absent form data and status into history for lecturer and student
            saveHistoryStudentApproved()
            saveHistoryLecturerApproved()
            //---------------------------------------------------------------------------------------//
            Toast.makeText(this,"Absent Form Approved",Toast.LENGTH_SHORT).show()

            //Go back to the Lecturer Activity
            val intent = Intent(this, LecturerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        //If the decline button is pressed
        binding.btnDecline.setOnClickListener {
            //Use Firebase to send notification to the student for the status of the absent form which is decline
            databaseAbsentRequest = FirebaseDatabase.getInstance().getReference("AbsentRequest")
            databaseAbsentRequest.child(userName!!).child(moduleName!!).setValue("Decline")
            Log.i("MyTag", "Decline")
            //Delete the Absent Form which is already reviewed
            databaseRemove = FirebaseDatabase.getInstance().getReference(("AbsentForm"))
            databaseRemove.child(lecturerName!!).child(userName).removeValue()
            //---------------------------------------------------------------------------------------//

            //Save the absent form data and status into history for lecturer and student
            saveHistoryLecturerRejected()
            saveHistoryStudentRejected()
            //-------------------------------------------------------------------------------------//

            //intent to Lecturer Activity
            Toast.makeText(this,"Absent Form Decline",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LecturerActivity::class.java)
            startActivity(intent)
            //----------------------------------------------------------------------------------------//
        }

    }

    //This function is to save the Absent Form History into Firebase for Lecturer which is Approved
    private fun saveHistoryLecturerApproved() {
        val approved = "Approved"
        val historyLecturer = LecturerHistory(moduleName,time, date, approved,name)
        databaseSaveHistory = FirebaseDatabase.getInstance().getReference("Absent Form History Lecturer").child(lecturerName).child(userName)
        databaseSaveHistory.setValue(historyLecturer)
    }

    //This function is to save the Absent Form History into Firebase for Lecturer which is Decline
    private fun saveHistoryLecturerRejected() {
        val rejected = "Rejected"
        val historyLecturer = LecturerHistory(moduleName,time, date, rejected,name)
        databaseSaveHistory = FirebaseDatabase.getInstance().getReference("Absent Form History Lecturer").child(lecturerName).child(userName)
        databaseSaveHistory.setValue(historyLecturer)
    }

    //This function is to save the Absent Form History into Firebase for Student  which is Approved
    private fun saveHistoryStudentApproved() {
        val approved = "Approved"
        val historyStudent = StudentHistory(moduleName,time, date, approved)
        databaseSaveHistory = FirebaseDatabase.getInstance().getReference("Absent Form History Student").child(userName).child(moduleName)
        databaseSaveHistory.setValue(historyStudent)
    }

    //This function is to save the Absent Form History into Firebase for Student which is Decline
    private fun saveHistoryStudentRejected() {
        val rejected = "Rejected"
        val historyStudent = StudentHistory(moduleName,time, date,rejected)
        databaseSaveHistory = FirebaseDatabase.getInstance().getReference("Absent Form History Student").child(userName).child(moduleName)
        databaseSaveHistory.setValue(historyStudent)
    }
}
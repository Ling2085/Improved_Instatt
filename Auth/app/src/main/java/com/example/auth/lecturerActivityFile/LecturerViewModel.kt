package com.example.auth.lecturerActivityFile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auth.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LecturerViewModel:ViewModel() {

    private val _userList = MutableLiveData<ArrayList<Users>>()
    val userList: LiveData<ArrayList<Users>> get() = _userList
    private lateinit var userDataList: ArrayList<Users>
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("AbsentForm")

    //Get the Absent Form Data From Firebase
    fun fetchUserList(lecturerName: String) {
        userDataList = arrayListOf()
        database.child(lecturerName).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userDataList.clear()
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
//                        Log.i("MyTag","4")
                        val user = i.getValue(Users::class.java)
                        userDataList.add(user!!)
                    }
                    _userList.value = userDataList
                }
                else{
                    userDataList.clear()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("LecturerViewModel", "Error fetching data: ${error.message}")
            }
        })
    }
}
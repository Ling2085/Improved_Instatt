package com.example.auth.lecturerActivityFile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.auth.UserRecycleViewAdapter
import com.example.auth.Users
import com.example.auth.databinding.FragmentLecturer3Binding
import com.google.firebase.database.DatabaseReference

class AbsentFormLecturerFragment : Fragment() {
    private lateinit var binding: FragmentLecturer3Binding
    private lateinit var userList: ArrayList<Users>
    private lateinit var userName : String
    private val lecturerViewModel: LecturerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLecturer3Binding.inflate(inflater,container,false)
        val root: View = binding.root

        userList = arrayListOf()
        //Show all the Absent Form in Recycler View
        observeLecturerViewModel()

        return root
    }


    private fun observeLecturerViewModel() {
//        val lecturerName = arguments?.getString("LecturerName")

        //Get username from shared preferences
        userName = getUsername(requireContext()).toString()
        Log.i("MyTag","the userName is $userName")
        //------------------------------------------------------------//

        //Get the AbsentForm Data from Lecturer View Model
        lecturerViewModel.fetchUserList(userName)
        //------------------------------------------------------------//

        //Show the Absent Form Data(Student Name and Student ID)  in Recycler View
        lecturerViewModel.userList.observe(viewLifecycleOwner, Observer { user->
            val rvUserAdapter = UserRecycleViewAdapter(user) { selectedUser ->
                // Start AbsentFormLecturerActivity for selected item (Student)
                //All the data will be sent into AbsentFormLecturerActivity by intent
                val intent = Intent(requireActivity(), AbsentFormLecturerActivity::class.java).apply {
                    putExtra("lecturerName",userName)
                    putExtra("Name", selectedUser.name)
                    putExtra("StudentID", selectedUser.id)
                    putExtra("Date", selectedUser.date)
                    putExtra("Time", selectedUser.time)
                    putExtra("ModuleName", selectedUser.moduleName)
                    putExtra("Reason", selectedUser.reason)
                    putExtra("userName",selectedUser.userName)
                }
                startActivity(intent)
            }
            binding.rvUsers.adapter = rvUserAdapter
        })

        binding.rvUsers.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        //-----------------------------------------------------------------------------------------------//
    }

    //This function is to get the username from shared preferences
    private fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    override fun onResume() {
        super.onResume()
        Log.i("MyTag","Resume")
        observeLecturerViewModel()
    }

}










//        lecturerViewModel.lecturerName.observe(viewLifecycleOwner, Observer { name ->
//            lecturerName = name
//            Log.d("MyTag","$lecturerName")
//        })
//        database = FirebaseDatabase.getInstance().getReference("AbsentForm").child(lecturerName!!) //Create node here
//        database.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.i("MyTag","3")
//                userList.clear()
//                if (snapshot.exists()) {
//                    for (i in snapshot.children) {
//                        Log.i("MyTag","4")
//                        val user = i.getValue(Users::class.java)
//                        userList.add(user!!)
//                    }
//                }
//                val rvUserAdapter = UserRecycleViewAdapter(userList) { selectedUser ->
//                    // Create an intent to start the next activity
//                    val intent = Intent(requireActivity(), AbsentFormLecturerActivity::class.java)
//                    // Pass data from the clicked item
//                    intent.putExtra("lecturerName",lecturerName)
//                    intent.putExtra("Name", selectedUser.name)
//                    intent.putExtra("StudentID", selectedUser.id)
//                    intent.putExtra("Date",selectedUser.date)
//                    intent.putExtra("Time",selectedUser.time)
//                    intent.putExtra("ModuleName",selectedUser.moduleName)
//                    intent.putExtra("Reason",selectedUser.reason)
//                    intent.putExtra("userName",selectedUser.userName)
//                    startActivity(intent)
//                }
//                binding.rvUsers.adapter = rvUserAdapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
//            }
//        })
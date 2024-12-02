package com.example.auth.studentActivityFile

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auth.ClassRecyclerViewAdapterWithoutClick
import com.example.auth.Classes
import com.example.auth.Date
import com.example.auth.DateRecyclerViewAdapter
import com.example.auth.R
import com.example.auth.databinding.FragmentStudent2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllClassFragmentStudent : Fragment() {
    private lateinit var binding: FragmentStudent2Binding
    private lateinit var classList: ArrayList<Classes>
    private var dateList = listOf(
        Date("Monday"),
        Date("Tuesday"),
        Date("Wednesday"),
        Date("Thursday"),
        Date("Friday"),
        Date("Saturday"),
        Date("Sunday"),
    )
    private lateinit var userName :String
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudent2Binding.inflate(inflater,container,false)
        val root: View = binding.root
        //Log.i("MyTag","testing")

        //Get username from shared preferences
        userName = getUsername(requireContext()).toString()
        //------------------------------------------------------------/

        //Create recycler view for every single day
        val rvDateRecyclerViewAdapter = DateRecyclerViewAdapter(dateList){ selectedItem ->
            showDialog(selectedItem.date)
        }

        binding.rvStudentAllClass.adapter = rvDateRecyclerViewAdapter
        binding.rvStudentAllClass.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        //------------------------------------------------------------------------------------//

        return root

    }

    //This function is to get the username from shared preferences
    private fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    //This function is to show the dialog which shows all the classes for the day that has been pressed
    private fun showDialog(date: String) {
        classList = arrayListOf()
        //Handle the dialog settings
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_class_list)
        //----------------------------------------------------//

        Log.i("MyTag",date)

        //Getting the data from Firebase and show it in recycler view
        val rvClass = dialog.findViewById<RecyclerView>(R.id.rvClassDialog)
        val tvNoClass = dialog.findViewById<TextView>(R.id.tvNoClass)
        val tvDateAllClass = dialog.findViewById<TextView>(R.id.tvDateAllClass)
        tvDateAllClass.text = date
        database = FirebaseDatabase.getInstance().getReference("Students").child(userName).child(date)
        database.addValueEventListener(object: ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    tvNoClass.text = ""
                    if(snapshot.hasChildren()){
                        Log.i("MyTag","GetData")
                        for (i in snapshot.children) {
                            val user = i.getValue(Classes::class.java)
                            classList.add(user!!)
                        }
                    }
                    else{
                        Log.i("MyTag","NoData")
                        tvNoClass.text = "No Class Today"
                    }
                }
                else{
                    Log.i("MyTag","NoData")
                    tvNoClass.text = "No Class Today"
                }
                rvClass.layoutManager = LinearLayoutManager(context)
                val adapter = ClassRecyclerViewAdapterWithoutClick(classList)
                rvClass.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        //----------------------------------------------------------------------------------------------------//

        //Show the dialog
        dialog.show()
    }


}
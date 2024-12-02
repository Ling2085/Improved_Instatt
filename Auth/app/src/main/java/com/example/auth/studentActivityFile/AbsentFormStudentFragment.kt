package com.example.auth.studentActivityFile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.auth.R
import com.example.auth.Users
import com.example.auth.databinding.FragmentStudent3Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

// Validation for all the things
// Do the selectable spinner for lecturer
class AbsentFormStudentFragment : Fragment() {
    private var isAllFieldChecked : Boolean = false
    private lateinit var binding: FragmentStudent3Binding
    private lateinit var databaseLecturer : DatabaseReference
    private lateinit var databaseSend : DatabaseReference
    private lateinit var lectureList: ArrayList<String>
    private lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudent3Binding.inflate(inflater,container,false)
        val root: View = binding.root
        userName = getUsername(requireContext()).toString()
        // Opens the time picker dialog for choosing the time
        binding.etTime.setOnClickListener {
            // Get current time
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                    val selectedTime = SimpleDateFormat("HH:mm", Locale.getDefault())
                        .format(Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, selectedHour)
                            set(Calendar.MINUTE, selectedMinute)
                        }.time)

                    binding.etTime.text = selectedTime
                },
                hour, minute, true
            )

            timePickerDialog.show()
        }
        //-----------------------------------------------------------------------------------------------//

        // Opens the date picker dialog for choosing the date
        binding.etDate.setOnClickListener {
            // Get the current date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Create a DatePickerDialog w
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->

                    val selectedDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                        .format(Calendar.getInstance().apply {
                            set(selectedYear, selectedMonth, selectedDay)
                        }.time)

                    // Update the TextView with the selected date
                    binding.etDate.text = selectedDate
                },
                year, month, day
            )

            // Set the DatePicker to spinner mode
            datePickerDialog.show()
        }
        //-----------------------------------------------------------------------------------------------//


        // Open searchable spinner for chosing the lecturer
        binding.SearchableSpinner.setOnClickListener{
            lectureList = arrayListOf()
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val mRowList = layoutInflater.inflate(R.layout.dialog_searchable_spinner,null)
            val mListView = mRowList.findViewById<ListView>(R.id.list_view)
            val searchView = mRowList.findViewById<EditText>(R.id.edit_text)

            builder.setView(mRowList)
            val dialog: AlertDialog = builder.create()

            Log.i("MyTag","I am here")
            databaseLecturer = FirebaseDatabase.getInstance().getReference("Lecturers").child("Name")
            databaseLecturer.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    lectureList.clear()
                    Log.i("MyTag","I am here2")
                    if (snapshot.exists()) {
                        for (i in snapshot.children) {
                            val key = i.getKey().toString()
                            lectureList.add(key)
                            Log.d("MyTag", key)
                        }
                    }

                    val mAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,lectureList)
                    mListView.adapter = mAdapter
                    searchView.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            mAdapter.filter.filter(s)
                        }

                        override fun afterTextChanged(s: Editable?) {}
                    })

                    mListView.setOnItemClickListener { _, _, position, _ ->
                        // Get the selected item
                        val selectedItem = mAdapter.getItem(position)
                        // Update the SearchableSpinner with the selected text
                        binding.SearchableSpinner.text = selectedItem

                        // Dismiss the dialog
                        dialog.dismiss()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }


            })

            dialog.show()

        }
        //-----------------------------------------------------------------------------------------------//

        //Submit the form and make sure the validation is done before submit
        binding.btnSubmit.setOnClickListener {
            isAllFieldChecked = checkAllFields()
            if(isAllFieldChecked) {
                sendDataToFireBase()
            }
        }
        //-----------------------------------------------------------------------------------------------//


        return root
    }

    //This function is to get the username from shared preferences
    private fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    //This function is to send the absent form data into Firebase Database
    private fun sendDataToFireBase() {
        databaseSend = FirebaseDatabase.getInstance().getReference("AbsentForm")
        val studentName= arguments?.getString("StudentName")
        val name = binding.etFirstName.text.toString()
        val id = binding.etStudentID.text.toString()
        val moduleName = binding.etModuleName.text.toString()
        val lecturerName = binding.SearchableSpinner.text.toString()
        val date = binding.etDate.text.toString()
        val time = binding.etTime.text.toString()
        val reason = binding.etReason.text.toString()


        val user = Users(userName, name,id, date, time, moduleName, lecturerName, reason)
        //nodes child
        databaseSend.child(lecturerName).child(userName).setValue(user)
            .addOnSuccessListener {
                binding.etFirstName.text.clear()
                binding.etStudentID.text.clear()
                binding.etModuleName.text.clear()
                binding.SearchableSpinner.text = ""
                binding.etDate.text = ""
                binding.etTime.text = ""
                binding.etReason.text.clear()

                Toast.makeText(context,"Absent Form Successfully Sent", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(context,"Fail to Sent Absent Form", Toast.LENGTH_LONG).show()
            }
    }

     @SuppressLint("SetTextI18n")
     //This function is to check the absent form to make sure all the fields is filled up
     private fun checkAllFields(): Boolean {
         if (binding.etFirstName.length() == 0) {
             binding.tvErrorName.text = "Please enter your name!"
             binding.tvErrorName.setTextColor(Color.RED)
             Toast.makeText(requireContext(),"Please enter your name!",Toast.LENGTH_SHORT).show()
             return false
         }
         if (binding.etFirstName.length() != 0){
             binding.tvErrorName.text = ""
         }

         if (binding.etStudentID.length() == 0) {
             binding.tvErrorID.text = "Please enter your student ID!"
             binding.tvErrorID.setTextColor(Color.RED)
             Toast.makeText(requireContext(),"Please enter your student ID!",Toast.LENGTH_SHORT).show()
             return false
         }
         if (binding.etStudentID.length() != 0){
             binding.tvErrorID.text = ""
         }

         if (binding.SearchableSpinner.length() == 0) {
             binding.tvErrorLecturer.text = "Please choose the lecturer!"
             binding.tvErrorLecturer.setTextColor(Color.RED)
             Toast.makeText(requireContext(),"Please choose the lecturer!",Toast.LENGTH_SHORT).show()
             return false
         }
         if (binding.SearchableSpinner.length() != 0){
             binding.tvErrorLecturer.text = ""
         }

         if (binding.etModuleName.length() == 0) {
             binding.tvErrorModule.text = "Please enter the module code!"
             binding.tvErrorModule.setTextColor(Color.RED)
             Toast.makeText(requireContext(),"Please enter the module code!",Toast.LENGTH_SHORT).show()
             return false
         }
         if (binding.etModuleName.length() != 0) {
             binding.tvErrorModule.text = ""
         }

         if (binding.etDate.length() == 0) {
             binding.tvErrorDate.text = "Please choose the date!"
             binding.tvErrorDate.setTextColor(Color.RED)
             Toast.makeText(requireContext(),"Please choose the date!",Toast.LENGTH_SHORT).show()
             return false
         }
         if (binding.etDate.length() != 0){
             binding.tvErrorDate.text = ""
         }

         if (binding.etTime.length() == 0) {
             binding.tvErrorTime.text = "Please choose the time!"
             binding.tvErrorTime.setTextColor(Color.RED)
             Toast.makeText(requireContext(),"Please choose the time!",Toast.LENGTH_SHORT).show()
             return false
         }
         if (binding.etTime.length() !=0){
             binding.tvErrorTime.text = ""
         }

         if (binding.etReason.length() == 0) {
             binding.tvErrorReason.text = "Please enter the reason!"
             binding.tvErrorReason.setTextColor(Color.RED)
             Toast.makeText(requireContext(),"Please enter the reason!",Toast.LENGTH_SHORT).show()
             return false
         }
         if (binding.etReason.length() != 0){
             binding.tvErrorReason.text = ""
         }
         // after all validation return true.
         return true
     }

//    private fun checkText():Boolean{
//
//    }
}

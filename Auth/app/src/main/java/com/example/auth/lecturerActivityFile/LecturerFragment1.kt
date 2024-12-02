package com.example.auth.lecturerActivityFile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.auth.ClassLecturerRecycleViewAdapter
import com.example.auth.Classes
import com.example.auth.databinding.FragmentLecturer1Binding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LecturerFragment1 : Fragment() {
    private lateinit var binding: FragmentLecturer1Binding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var classList: ArrayList<Classes>
    private lateinit var databaseClass: DatabaseReference
    private lateinit var userName : String
    private lateinit var moduleCode : String
    private var currentLatitude : Double ?= null
    private var currentLongitude : Double ?= null
    private lateinit var currentDay :String

    //Launcher of permission for location
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation(){
                    qrCodeActivity()
                }
            } else {
                Toast.makeText(context, "Location permission is required to get your current location", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLecturer1Binding.inflate(inflater,container,false)
        val root: View = binding.root

        // To get the current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //--------------------------------------------------------------------------------------------//


//        lecturerName = arguments?.getString("LecturerName").toString()

        //Get username from shared preferences
        userName = getUsername(requireContext()).toString()
        Log.i("MyTag","the userName is $userName")
        //------------------------------------------------------------//


        //Update to the current day
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        currentDay = dayFormat.format(calendar.time)
        binding.tvDate2.text = currentDay
        //-------------------------------//


        //For adding class into the recycler view in the fragment for specific day
        classList = arrayListOf()
        databaseClass = FirebaseDatabase.getInstance().getReference("Lecturers").child(userName!!).child(currentDay)
        databaseClass.addListenerForSingleValueEvent( object: ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                classList.clear()
                if (snapshot.exists()) {
                    binding.tvNoClassLecturer.text = ""
                    if(snapshot.hasChildren()) {
                        for (i in snapshot.children) {
                            Log.i("MyTag", "Class list get2")
                            val user = i.getValue(Classes::class.java)
                            classList.add(user!!)
                        }
                    }
                    else{
                        Log.i("MyTag","NoData")
                        binding.tvNoClassLecturer.text = "No Class Today"
                    }
                }
                else{
                    Log.i("MyTag","NoData")
                    binding.tvNoClassLecturer.text = "No Class Today"
                }
                val rvClassAdapter =  ClassLecturerRecycleViewAdapter(classList){selectedClass ->
                    checkLocationPermission() //Get Permission and Current Location Here
                    moduleCode = selectedClass.codeModule.toString()
                }
                binding.rvClass2.adapter = rvClassAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "No Class", Toast.LENGTH_LONG).show()
            }

        })
        binding.rvClass2.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        //---------------------------------------------------------------------------------------//

        return root
    }

    //This function is to get the username from shared preferences
    private fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    //This function is to direct the lecturer into QR code Activity when a class is pressed
    //Code Module and Location data will be sent into QR code Activity using Intent
    private fun qrCodeActivity() {
        if (currentLatitude != null && currentLongitude != null) {
            val intent = Intent(requireActivity(), QRCodeActivity::class.java).apply {
                putExtra("codeModule", moduleCode)
                putExtra("currentLatitude", currentLatitude)
                putExtra("currentLongitude", currentLongitude)
            }
//            Log.i("MyTag",moduleCode)
//            Log.i("MyTag",currentLatitude.toString())
//            Log.i("MyTag",currentLongitude.toString())
            startActivity(intent)
        } else {
            Toast.makeText(context, "Location not available. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    //This function is to check the permission of location to ensure the location can be accessed
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation(){
                qrCodeActivity()
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    //This function is to get the current location of the lecturer
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(onLocationReady: () -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    currentLatitude = it.latitude
                    currentLongitude = it.longitude
                    Log.i("MyTag","Location acquired, $currentLongitude , $currentLatitude")
                    onLocationReady()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
//        Log.i("MyTag","Fragment 1 Resume")
    }


}
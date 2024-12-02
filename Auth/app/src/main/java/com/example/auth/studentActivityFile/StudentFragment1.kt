package com.example.auth.studentActivityFile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
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
import com.example.auth.ClassRecycleViewAdapter
import com.example.auth.Classes
import com.example.auth.databinding.FragmentStudent1Binding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//create signed in using qr code if possible
class StudentFragment1 : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentStudent1Binding
    private lateinit var databaseClass: DatabaseReference
    private lateinit var databaseSignIn: DatabaseReference
    private lateinit var classList: ArrayList<Classes>
    private lateinit var userName :String
    private var currentLatitude : Double ?= null
    private var currentLongitude : Double ?= null
    private lateinit var selectedModuleCode: String
    private lateinit var currentDay :String

    //Launcher of permission for camera
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
            if(isGranted){ //If the permission is granted, check the location permission
                checkLocationPermission()
            }
        }

    //Launcher of permission for location
    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {//If the permission is granted, open the camera for scanning qr code
                showCamera()
            } else { //If the permission is not granted, inform the user through Toast
                Toast.makeText(context, "Location permission is required to get your current location", Toast.LENGTH_SHORT).show()
            }
        }

    //Scanner of QR code
    private val scanLauncher =
        registerForActivityResult(ScanContract()){ result: ScanIntentResult ->
            run {
                if (result.contents == null) { //If nothing is obtained from QR code such as user press back button
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                } else { //If a QR code is scanned
                    getCurrentLocation {
                        parseData(result.contents) //Only works after the current location of the student
                    }
                }
            }
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudent1Binding.inflate(inflater,container,false)
        val root: View = binding.root

        //Get username from shared preferences
        userName = getUsername(requireContext()).toString()
        Log.i("MyTag","the userName is $userName")
        //------------------------------------------------------------//

        // To get the current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        //-------------------------------//

        //Update to the current day
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        currentDay = dayFormat.format(calendar.time).toString()
        binding.tvDate.text = currentDay
        //-------------------------------//


        //For adding class into the recycler view in the fragment for specific day
        classList = arrayListOf()
        databaseClass = FirebaseDatabase.getInstance().getReference("Students").child(userName!!).child(currentDay)
        databaseClass.addListenerForSingleValueEvent( object: ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                classList.clear()
                if (snapshot.exists()) {
                    binding.tvNoClassStudent.text = ""
                    if(snapshot.hasChildren()) {
                        for (i in snapshot.children) {
                            Log.i("MyTag", "Class list get")
                            val user = i.getValue(Classes::class.java)
                            classList.add(user!!)
                        }
                    }
                    else{
                        Log.i("MyTag","NoData")
                        binding.tvNoClassStudent.text = "No Class Today"
                    }
                }
                else{
                    Log.i("MyTag","NoData")
                    binding.tvNoClassStudent.text = "No Class Today"
                }
                val rvClassAdapter =  ClassRecycleViewAdapter(classList){ selectedClass ->
                    //Open the qrcode
                    //Before open the qr code must check the permission of camera and location
                    checkPermissionCamera(requireContext())
                    selectedModuleCode = selectedClass.codeModule.toString()
                }
                binding.rvClass.adapter = rvClassAdapter
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "No Class", Toast.LENGTH_LONG).show()
            }

        })

        binding.rvClass.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        //---------------------------------------------------------------------------------------//


        //database.keepSynced(true)

        return root
    }

    //This function is to get the username from shared preferences
    private fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    @SuppressLint("MissingPermission")
    //This function is to get the current location of the student
    private fun getCurrentLocation(onLocationReady: () -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                currentLatitude = it.latitude
                currentLongitude = it.longitude
                Toast.makeText(context, "Location acquired", Toast.LENGTH_SHORT).show()
                Log.i("MyTag","Location acquired, $currentLongitude , $currentLatitude")
                onLocationReady() //If the current location is obtained,callback for parsing the data from QR code
            } ?: Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show()
        }
    }

    //This function is to process the data obtained from QR code and determine whether the student can be sign in
    private fun parseData(data: String) {
        val parts = data.split(",") //Since the data is separated with comma so this function is used to obtain all the data
        if (parts.size == 4) {
            val moduleCode = parts[0]
            val latitudeLecturer = parts[1].toDoubleOrNull()
            val longitudeLecturer = parts[2].toDoubleOrNull()
            val dateTime = parts[3].toString()
            Log.i("MyTag","$moduleCode and $latitudeLecturer and  $longitudeLecturer")

            //Check whether the module is correct
            if (moduleCode == selectedModuleCode) {
                //To convert the date and time data from qr code to simple date format for comparison
                val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                val qrTime = simpleDateFormat.parse(dateTime)

                //To get current date and time for student
                val currentTime = Date()

                // To get time difference
                val timeDifference = currentTime.time - qrTime!!.time
                Log.i("MyTag", timeDifference.toString())

                //Set the qr code only work for 30 minutes
                if (timeDifference <= 30 * 60 * 1000) {
                    //Ensure there is location in QR code
                    if (latitudeLecturer != null && longitudeLecturer != null) {
                        //Check whether the student is within the range of the classroom
                        if (isWithinRange(currentLatitude!!, currentLongitude!!, latitudeLecturer, longitudeLecturer)) {
                            //Update the Sign in Status into true
                            databaseSignIn = FirebaseDatabase.getInstance().getReference("Students").child(userName).child(currentDay).child(moduleCode).child("signIn")
                            databaseSignIn.setValue("True")
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Attendance Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Fail to Upload to FireBase",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                        //If the student not in the range of the classroom
                        else {
                            Toast.makeText(
                                context,
                                "Location incorrect. Please make sure u are inside the class.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        //If the QR code don't have location
                    } else {
                        Toast.makeText(context, "There is no location in the QR Code", Toast.LENGTH_SHORT).show()
                    }
                }
                //If the QR code is scanned after 30 minutes
                else{
                    Toast.makeText(context, "This QR Code is expired", Toast.LENGTH_SHORT).show()
                }
            } //If the wrong module QR Code is scanned
            else{
                Toast.makeText(context, "Incorrect Module", Toast.LENGTH_SHORT).show()
            }
        }
        //If other QR code is scanned
        else {
            Toast.makeText(context, "Invalid QR code format", Toast.LENGTH_SHORT).show()
        }
    }

    // This function is to check the distance between the student location and lecturer location
    private fun isWithinRange(currentLat: Double, currentLon: Double, latLecturer: Double, longLecturer: Double): Boolean {
        val results = FloatArray(1)
        Location.distanceBetween(currentLat, currentLon, latLecturer, longLecturer, results)
        Log.i("MyTag", results[0].toString())
        // 50 meters is chosen
        return results[0] < 50
    }

    // This function is to open the camera for scanning the QR code
    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(true)

        scanLauncher.launch((options))
    }

    //This function is to check permission of camera to ensure the qr code can be scanned
    private fun checkPermissionCamera(context: Context) {
        if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            checkLocationPermission()//After check camera permission check location permission
        }
        else if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(context,"CAMERA permission required",Toast.LENGTH_SHORT).show()
        }
        else{ //If no permission for camera
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    //This function is to check the permission of location to ensure the location can be accessed
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            showCamera() //If the location permission is enabled, the camera will be started for scanning qr code
        } else {
            //If the location permission is not enabled, request the user for enabling location permission
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    override fun onResume() {
        super.onResume()
        classList = arrayListOf()
        databaseClass = FirebaseDatabase.getInstance().getReference("Students").child(userName!!).child(currentDay)
        databaseClass.addListenerForSingleValueEvent( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                classList.clear()
                Log.i("MyTag", "classList.clear")
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        Log.i("MyTag", "Class list get")
                        val user = i.getValue(Classes::class.java)
                        classList.add(user!!)
                    }
                }
                val rvClassAdapter = ClassRecycleViewAdapter(classList) { selectedClass ->
                    //Open the qrcode
                    checkPermissionCamera(requireContext())
                    selectedModuleCode = selectedClass.codeModule.toString()
                }
                binding.rvClass.adapter = rvClassAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "No Class", Toast.LENGTH_LONG).show()
            }
        })
    }

}
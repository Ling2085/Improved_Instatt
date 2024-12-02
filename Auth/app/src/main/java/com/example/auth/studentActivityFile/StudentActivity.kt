package com.example.auth.studentActivityFile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.auth.R
import com.example.auth.databinding.ActivityStudentBinding
import com.example.auth.databinding.NavDrawerHeaderBinding
import com.example.auth.drawerActivityFile.AboutUsActivity
import com.example.auth.drawerActivityFile.SettingsActivity
import com.example.auth.signInActivityFile.SignInActivity
import com.example.auth.signInActivityFile.SignInViewModel
import com.example.auth.signInActivityFile.SignInViewModelFactory
import com.example.auth.signInActivityFile.UserRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class StudentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //Declared values
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityStudentBinding
    private lateinit var studentName: String

    private val signInViewModel: SignInViewModel by viewModels {
        SignInViewModelFactory(UserRepository())
    }
    //---------------------------------------------------------------------------------------------------//

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                //Toast.makeText(this, "Notification permission granted",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please grant the notification permission in settings", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        studentName = intent.getStringExtra("Name").toString()
//        replaceFragment(StudentFragment1(),"FRAGMENT_1")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }

        // To get the current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //--------------------------------------------------------------------------------------------//

        //Start the service for the notification
        //for student for getting notification for the status of absent form
        //after the absent form has been reviewed
        val serviceIntent = Intent(this, NotificationServiceStudent::class.java)
        serviceIntent.putExtra("Name", studentName)
        ContextCompat.startForegroundService(applicationContext, serviceIntent)
        //--------------------------------------------------------------------------------------------//

        //Getting user details
        signInViewModel.checkUserDetails()
        //--------------------------------------//

//        Log.i("MyTag", "Start Notification")

        //Use signInViewModel to get the userName for the lecturer
        //The data will be save into the shared preference
        //So ,other fragment and activity can access the username through shared preferences
        signInViewModel.userName.observe(this, Observer { userName ->
            studentName = userName!!
            Log.i("MyTag", "the student is $studentName")
            val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", studentName)
            editor.apply()
        })
        //--------------------------------------------------------------------------------------------------//

        //Email is obtained to change the email in the navigation drawer into user email
        signInViewModel.userEmail.observe(this, Observer { email ->
            val headerView: View = binding.navView.getHeaderView(0)
            val headerBinding: NavDrawerHeaderBinding = NavDrawerHeaderBinding.bind(headerView)
            headerBinding.tvEmail.text = email
        })
        //--------------------------------------------------------------------------------------------------//

        // For handle navigation drawer
        binding.navView.setNavigationItemSelectedListener(this)
        val drawerLayout = binding.main
        val menuButton: ImageButton = binding.ibMenu
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(binding.navView)
        }
        //------------------------------------------------------------//

        //Changing the fragment using bottom navigation (nav controller is used)
        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.FrameLayout) as NavHostFragment
        val navController = navigationHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        //------------------------------------------------------------//
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    @SuppressLint("MissingSuperCall")
    //When the back button in phone is pressed, make sure the navigation drawer will be closed first then only back to normal back pressed
    override fun onBackPressed() {
//        super.onBackPressed()
        if (binding.main.isDrawerOpen(GravityCompat.START)) {
            binding.main.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.addCategory(Intent.CATEGORY_HOME)
//            startActivity(intent)
        }
    }

    //When the item in the navigation drawer is selected
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_item -> settingsActivity()
            R.id.logout_item -> logoutActivity()
            R.id.about_us_item -> aboutUsActivity()
            R.id.absent_history_item -> absentFormHistoryActivity()
        }
        binding.main.closeDrawer(GravityCompat.START)
        return true
    }

    //This function is to direct the user into Absent Form History page
    private fun absentFormHistoryActivity() {
        val intent = Intent(this, AbsentFormHistoryStudentActivity::class.java)
        startActivity(intent)
    }

    //This function is to direct the user into About Us Page
    private fun aboutUsActivity() {
        val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)
    }

    //This function is to log out and direct the user into Sign In Page
    private fun logoutActivity() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    //This function is to direct the user into Setting Page
    private fun settingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    //This function is to check the notification permission
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this,"Notification permission done",Toast.LENGTH_SHORT).show()
        } else {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

//    override fun onResume() {
//        super.onResume()
////        Log.i("MyTag", "Student Activity Resume")
////        replaceFragment(StudentFragment1(),"FRAGMENT_1")
//    }



//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()){
//                isGranted: Boolean ->
//            if(isGranted){
//                showCamera()
//            }
//        }

//    private val locationPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                getCurrentLocation()
//            } else {
//                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show()
//            }
//        }

//    private val scanLauncher =
//        registerForActivityResult(ScanContract()){ result: ScanIntentResult ->
//            run {
//                if (result.contents == null) {
//                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
//                } else {
//                    parseData(result.contents)
//                    Toast.makeText(this, "Attendance Done", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }


//    private fun checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            getCurrentLocation()
//        } else {
//            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }

//    private fun checkPermissionCamera(context: Context) {
//        if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
//            showCamera()
//        }
//        else if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
//            Toast.makeText(context,"CAMERA permission required",Toast.LENGTH_SHORT).show()
//        }
//        else{ //If no permission for camera
//            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
//        }
//    }


//    private fun showCamera() {
//        val options = ScanOptions()
//        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
//        options.setPrompt("Scan QR Code")
//        options.setCameraId(0)
//        options.setBeepEnabled(false)
//        options.setBarcodeImageEnabled(true)
//        options.setOrientationLocked(true)
//
//        scanLauncher.launch((options))
//    }

//private fun parseData(data: String) { //where the data is obtained
//    val parts = data.split(",")
//    if (parts.size == 3) {
//        val moduleCode= parts[0]
//        val latitudeLecturer = parts[1].toDoubleOrNull()
//        val longitudeLecturer = parts[2].toDoubleOrNull()
//
//        if (latitudeLecturer != null && longitudeLecturer != null) {
//            if (isWithinRange(currentLatitude!!, currentLongitude!!, latitudeLecturer, longitudeLecturer)) {
//                Toast.makeText(this, "Attendance Successful", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                Toast.makeText(this, "Location incorrect. Please make sure u are inside the class.", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Toast.makeText(this, "Invalid QR code format", Toast.LENGTH_SHORT).show()
//        }
//    } else {
//        Toast.makeText(this, "Invalid QR code format", Toast.LENGTH_SHORT).show()
//    }
//}
//
//private fun isWithinRange(currentLat: Double, currentLon: Double, latLecturer: Double, longLecturer: Double): Boolean {
//    val results = FloatArray(1)
//    Location.distanceBetween(currentLat, currentLon, latLecturer, longLecturer, results)
//    return results[0] < 50 // Define the acceptable range in meters (e.g., 50 meters)
//}

//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.miHome2 -> replaceFragment(StudentFragment1(),"FRAGMENT_1")
//                R.id.miClasses2 -> replaceFragment(StudentFragment2(),"FRAGMENT_2")
//                R.id.miForm2 -> replaceFragment(StudentFragment3(),"FRAGMENT_3")
//
//                else ->{
//                }
//            }
//            true
//        }

//    private fun replaceFragment(fragment: Fragment,tag: String){
////        Log.i("MyTag","The student name is $studentName")
////        val bundle = Bundle()
////        bundle.putString("StudentName", studentName)
////        fragment.arguments = bundle
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.FrameLayout,fragment,tag)
//        fragmentTransaction.addToBackStack(tag) // Add to back stack with a tag
//        fragmentTransaction.commit()
//    }
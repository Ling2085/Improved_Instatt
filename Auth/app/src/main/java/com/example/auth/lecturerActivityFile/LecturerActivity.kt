package com.example.auth.lecturerActivityFile

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
import com.example.auth.studentActivityFile.AbsentFormHistoryStudentActivity
import com.example.auth.drawerActivityFile.AboutUsActivity
import com.example.auth.drawerActivityFile.SettingsActivity
import com.example.auth.R
import com.example.auth.signInActivityFile.SignInActivity
import com.example.auth.databinding.ActivityLecturerBinding
import com.example.auth.databinding.NavDrawerHeaderBinding
import com.example.auth.signInActivityFile.SignInViewModel
import com.example.auth.signInActivityFile.SignInViewModelFactory
import com.example.auth.signInActivityFile.UserRepository
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class LecturerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityLecturerBinding
    private lateinit var lecturerName :String
    private val signInViewModel: SignInViewModel by viewModels{
        SignInViewModelFactory(UserRepository())
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notification permission granted",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please grant the notification permission in settings", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityLecturerBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        replaceFragment(LecturerFragment1()) //here will get lecturer name first

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }
        lecturerName = intent.getStringExtra("Name").toString()


        //Start the service for the notification for lecturer
        //for getting notification for new absent form which needs to be review
        val serviceIntent = Intent(this, NotificationService::class.java)
        serviceIntent.putExtra("Name", lecturerName)
        ContextCompat.startForegroundService(applicationContext, serviceIntent)
        Log.i("MyTag", "Start Notification")
        //-------------------------------------------------------------------------------------//

        //Getting user details
        signInViewModel.checkUserDetails()
        //--------------------------------------//

        //Use signInViewModel to get the userName for the lecturer
        //The data will be save into the shared preference
        //So ,other fragment and activity can access the username through shared preferences
        signInViewModel.userName.observe(this, Observer{ userName ->
            lecturerName= userName!!
            Log.i("MyTag","Hi my name is $lecturerName")
            val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", lecturerName)
            editor.apply()
        })
        //--------------------------------------------------------------------------------------------------//

        //Email is obtained to change the email in the navigation drawer into user email
        signInViewModel.userEmail.observe(this, Observer { email ->
            Log.i("MyTag", "the email is $email")
            val headerView: View = binding.navView.getHeaderView(0)
            val headerBinding: NavDrawerHeaderBinding = NavDrawerHeaderBinding.bind(headerView)
            headerBinding.tvEmail.text = email
            Log.i("MyTag", "the email is $email")
        })
        //--------------------------------------------------------------------------------------------------//

        //For drawer navigation view//
        binding.navView.setNavigationItemSelectedListener(this)
        val drawerLayout = binding.main
        val menuButton: ImageButton = binding.ibMenu2
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(binding.navView)
        }
        //-------------------------------------------------------//

        //For bottom navigation view//
        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.FrameLayout) as NavHostFragment
        val navController = navigationHost.navController
        binding.bottomNavigationView2.setupWithNavController(navController)
        //--------------------------------------------------------------//


    }

    //When the item in the navigation drawer is selected
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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
        val intent = Intent(this, AbsentFormHistoryLecturerActivity::class.java)
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


    @SuppressLint("MissingSuperCall")
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    //When the back button in phone is pressed, make sure the navigation drawer will be closed first then only back to normal back pressed
    override fun onBackPressed() {
    if(binding.main.isDrawerOpen(GravityCompat.START)){
            binding.main.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.addCategory(Intent.CATEGORY_HOME)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
        }
    }

    //This function is to check the notification permission
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //Toast.makeText(this, "Notification permission done", Toast.LENGTH_SHORT).show()
        } else {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
//    override fun onResume() {
//        super.onResume()
//        Log.i("MyTag", "Lecturer Activity Resume")
//        signInViewModel.userName.observe(this, Observer{ userName ->
//            lecturerName= userName!!
//            Log.i("MyTag","Hi my name is $lecturerName")
//        })
//
//        signInViewModel.userEmail.observe(this, Observer { email ->
//            Log.i("MyTag", "the email is $email")
//            val headerView: View = binding.navView.getHeaderView(0)
//            val headerBinding: NavDrawerHeaderBinding = NavDrawerHeaderBinding.bind(headerView)
//            headerBinding.tvEmail.text = email
//            Log.i("MyTag", "the email is $email")
//        })
//        replaceFragment(LecturerFragment1())
//    }
}


//        binding.bottomNavigationView2.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.miHome -> replaceFragment(LecturerFragment1())
//                R.id.miClasses -> replaceFragment(LecturerFragment2())
//                R.id.miForm -> replaceFragment(LecturerFragment3())
//                else ->{
//
//                }
//            }
//            true
//        }
//    private fun replaceFragment(fragment: Fragment){
////        Log.d("MyTag", "Name is $lecturerName")
//
////        lecturerName.let {
////            lecturerViewModel.setLecturerName(it)
////            Log.d("MyTag", "Lecturer Name: $it")
////        }
//
////        val bundle = Bundle()
////        Log.d("MyTag", "bundle")
////        bundle.putString("LecturerName", lecturerName)
////        fragment.arguments = bundle
//
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.FrameLayout,fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//
//
//    }

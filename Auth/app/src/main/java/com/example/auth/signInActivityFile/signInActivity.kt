package com.example.auth.signInActivityFile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.auth.lecturerActivityFile.LecturerActivity
import com.example.auth.R
import com.example.auth.studentActivityFile.StudentActivity
import com.example.auth.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private var isAllFieldChecked : Boolean = false
    private val signInViewModel: SignInViewModel by viewModels {
        SignInViewModelFactory(UserRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //First Check the user already sign in or not
        //If yes direct the user to the main page
        //If no need to sign in first
        signInViewModel.checkUserAlreadySignedIn()


        signInViewModel.signInStatus.observe(this, Observer { isSuccess ->
            if (isSuccess == false) {
                Toast.makeText(this, "Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        })

        //the type and name of sign in view model will be observed and according to the type of the user
        signInViewModel.userType.observe(this, Observer { type ->
            signInViewModel.userName.observe(this,Observer { userName ->
                if (userName != null && type != null) {
                    val intent = if (type == "student") {
                        Intent(this, StudentActivity::class.java)
                    } else {
                        Intent(this, LecturerActivity::class.java)
                    }
//                    Log.i("MyTag", "User is $userName")
                    intent.putExtra("Name", userName)
                    startActivity(intent)
                    finish()
                }
            })
        })


        //Send the data into signInViewModel for authentication
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (checkAllFields()) {
                signInViewModel.signIn(email, password)
            }
        }
    }


    //This function is to do validation to ensure that all the box is filled with data
    @SuppressLint("SetTextI18n")
    private fun checkAllFields(): Boolean {
        if (binding.etEmail.length() == 0) {
            binding.tvEmailError.text = "Please enter your email!"
            binding.tvEmailError.setTextColor(Color.RED)
            Toast.makeText(this,"Please enter your email!",Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etEmail.length() != 0){
            binding.tvEmailError.text = ""
        }

        if (binding.etPassword.length() == 0) {
            binding.tvPasswordError.text = "Please enter your password!"
            binding.tvPasswordError.setTextColor(Color.RED)
            Toast.makeText(this,"Please enter your password!",Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPassword.length() != 0){
            binding.tvPasswordError.text = ""
        }
        // after all validation return true.
        return true
    }
}


//override fun onResume() {
  //  super.onResume()
//        Log.i("MyTag", "OnResume")
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        if (currentUser != null) {
//            signInViewModel.userType.observe(this, Observer { type ->
//                signInViewModel.userName.observe(this,Observer { userName ->
//                    if (userName != null && type != null) {
//                        val intent = if (type == "student") {
//                            Intent(this, StudentActivity::class.java)
//                        } else {
//                            Intent(this, LecturerActivity::class.java)
//                        }
//                        intent.putExtra("Name", userName)
//                        startActivity(intent)
//                    }
//                })
//            })
//        }
//}

//        auth = Firebase.auth
//        val user = Firebase.auth.currentUser

//        if (user != null) { //Check whether the user already login or not
//            Log.d("MyTag", "start")
//            checkStudentOrLecturer()
//        } else {
//            binding.btnSignIn.setOnClickListener {
//                isAllFieldChecked = checkAllFields()
//                if (checkAllFields()){
//                    checkEmailAndPassword()
//                }
//            }
//        }


//    private fun checkEmailAndPassword() {
//        val email = binding.etEmail.text.toString()
//        val password = binding.etPassword.text.toString()
//
//        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
//            if(it.isSuccessful){
//                checkStudentOrLecturer()
//            }
//            else{
//                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    private fun checkStudentOrLecturer() {
//        val db = Firebase.firestore
//        val user1 = auth.currentUser
//        val uid = user1!!.uid
//        db.collection("users")
//            .document(uid)
//            .get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    val userName = document.getString("Name").toString()
//                    val userType = document.getString("type").toString()
////                            Log.d("MyTag", "DocumentSnapshot data: $userName")
////                            Log.d("MyTag", "DocumentSnapshot data: $userType")
//                    if(userType == "student"){
//                        val intent = Intent(this, StudentActivity::class.java)
//                        intent.putExtra("dataKey", userName)
//                        startActivity(intent)
//                    }
//                    else{
//                        val intent = Intent(this, LecturerActivity::class.java)
//                        intent.putExtra("dataKey", userName)
//                        startActivity(intent)
//                        val serviceIntent = Intent(this, Notification::class.java)
//                        serviceIntent.putExtra("Name", userName)
//                        ContextCompat.startForegroundService(this, serviceIntent)
//                        Log.d("MyTag", "LecturerLogin")
//
//                    }
//                } else {
//                    Log.d("MyTag", "No such document")
//                }
//            }
//            .addOnFailureListener{
//                Log.d("MyTag", "uid failed to get")
//            }
//    }
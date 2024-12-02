package com.example.auth.drawerActivityFile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.auth.R
import com.example.auth.databinding.ActivityQrcodeBinding
import com.example.auth.databinding.ActivitySettingsBinding
import com.example.auth.lecturerActivityFile.LecturerActivity
import com.example.auth.signInActivityFile.SignInViewModel
import com.example.auth.signInActivityFile.SignInViewModelFactory
import com.example.auth.signInActivityFile.UserRepository
import com.example.auth.studentActivityFile.StudentActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val signInViewModel: SignInViewModel by viewModels {
        SignInViewModelFactory(UserRepository())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signInViewModel.checkUserDetails()
        binding.iBback.setOnClickListener{
            signInViewModel.userType.observe(this, Observer { type ->
                signInViewModel.userName.observe(this, Observer { userName ->
                    if (userName != null && type != null) {
                        val intent = if (type == "student") {
                            Intent(this, StudentActivity::class.java)

                        } else {
                            Intent(this, LecturerActivity::class.java)
                        }
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        intent.putExtra("Name", userName)
                        startActivity(intent)
                    }
                })
            })
        }
    }

}
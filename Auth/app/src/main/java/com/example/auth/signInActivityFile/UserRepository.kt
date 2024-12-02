package com.example.auth.signInActivityFile

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //This function is to sign in in the Firebase Auth
    fun authentication(email: String, password: String, callback:(Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user1 = auth.currentUser
                val uid = user1!!.uid
                callback(true, uid)
            } else {
                callback(false, "")
            }

        }
    }

    //This function is to get the user information which is student or lecturer and email in Firebase Store
    fun getUserType(uid:String,callback:(String,String,String) -> Unit){
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userName = document.getString("Name").toString()
                    val userType = document.getString("type").toString()
                    val userEmail =document.getString("Email").toString()
//                    Log.i("MyTag", "User Repository the email is $userEmail")
                    callback(userType, userName,userEmail)
                }
            }
    }

    //This function is to get the user email and name from Firebase Auth
    fun getCurrentUser(callback:(Boolean, String, String) -> Unit) {
        val user = Firebase.auth.currentUser
        val email = user?.email
        if (user != null ){
            val uid = user.uid
            callback(true, uid, email!!)
        }
        else{
            callback(false, "", "")
        }
    }

}
package com.example.auth

//This data class handles the absent form data
data class Users (
    val userName : String? = null,
    val name : String? = null,
    val id : String? = null,
    val date : String? = null,
    val time : String? = null,
    val moduleName :String? = null,
    val lecturerName : String? = null,
    val reason : String? = null
){}

package com.example.auth

//This data class handles the absent form history for student
data class StudentHistory (
    val codeModule : String? = null,
    val time : String? = null,
    val date : String? = null,
    val approvedOrReject : String? = null,
)
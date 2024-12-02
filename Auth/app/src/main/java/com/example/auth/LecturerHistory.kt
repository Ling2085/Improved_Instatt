package com.example.auth

//This data class handles the absent form history for lecturer
data class LecturerHistory(
    val codeModule : String? = null,
    val time : String? = null,
    val date : String? = null,
    val approvedOrReject : String? = null,
    val studentName : String? = null
)
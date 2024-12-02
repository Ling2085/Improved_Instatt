package com.example.auth.lecturerActivityFile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.auth.databinding.ActivityQrcodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class QRCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding
    private lateinit var dateTime :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // To get the current date and time
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        dateTime = simpleDateFormat.format(calendar.time).toString()
        //---------------------------------------------------------------------------------------------//

        //Handle back navigation
        binding.iBback.setOnClickListener{
            val intent = Intent(this, LecturerActivity::class.java)
            startActivity(intent)
        }
        //---------------------------------------------------------------------------------------------//

        //Get the codeModule and current location data from the LecturerFragment1
        val codeModule = intent.getStringExtra("codeModule")
        val currentLatitude = intent.getDoubleExtra("currentLatitude", 0.0000000)
        val currentLongitude = intent.getDoubleExtra("currentLongitude",0.0000000)
        //---------------------------------------------------------------------------------------------//

//        Log.i("MyTag",codeModule!!)
//        Log.i("MyTag",currentLatitude.toString())
//        Log.i("MyTag",currentLongitude.toString())
        //Generate QR Code
        val qrCodeBitmap = generateQRCode(codeModule!!,currentLatitude,currentLongitude)
        if (qrCodeBitmap != null) {
           binding.ivQRCode.setImageBitmap(qrCodeBitmap)
        }
        //--------------------------------------------------------------------------------------------//
    }

    //This function is to generate QR code for the data
    private fun generateQRCode(moduleCode: String, latitude: Double, longitude: Double): Bitmap? {
        //Create a string with all the information
        val qrData = "$moduleCode,$latitude,$longitude,$dateTime"

        val writer = QRCodeWriter()
        return try {
            val size = 1024
            val bitMatrix = writer.encode(qrData, BarcodeFormat.QR_CODE, size, size)
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

            // Draw QR code on the bitmap
            for (x in 0 until size) {
                for (y in 0 until size) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}
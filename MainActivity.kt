package com.example.flashlightapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var isFlashOn = false
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashButton: ImageButton = findViewById(R.id.flashButton)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList[0]

        flashButton.setOnClickListener {
            toggleFlashLight()
        }

        // التأكد من وجود صلاحيات الكاميرا
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        }
    }

    private fun toggleFlashLight() {
        try {
            if (isFlashOn) {
                cameraManager.setTorchMode(cameraId, false)
                isFlashOn = false
                Toast.makeText(this, "تم إطفاء الفلاش", Toast.LENGTH_SHORT).show()
            } else {
                cameraManager.setTorchMode(cameraId, true)
                isFlashOn = true
                Toast.makeText(this, "تم تشغيل الفلاش", Toast.LENGTH_SHORT).show()
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
            Toast.makeText(this, "حدث خطأ في الوصول إلى الفلاش", Toast.LENGTH_SHORT).show()
        }
    }
}

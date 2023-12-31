package com.example.kotlinauthorizationwithcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        LoadActivity()
    }

    private fun LoadActivity() {

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.progress = 0
        progressBar.max = 100

        val duration = 2000
        val increment = 1

        val handler = Handler()
        val runnable = object : Runnable {
            var progress = 0

            override fun run() {
                progress += increment
                progressBar.progress = progress

                if (progress >= progressBar.max) {

                    val intent = if (DataSettings.isHaveData) {
                        Intent(this@SplashActivity, MainActivity::class.java)
                    } else {
                        Intent(this@SplashActivity, RegistrationActivity::class.java)
                    }

                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    handler.removeCallbacks(this)
                    startActivity(intent)
                    finish()
                } else {
                    DataSettings.LoadData(this@SplashActivity)
                    handler.postDelayed(this, (duration / (progressBar.max / increment)).toLong())
                }
            }
        }

        progressBar.visibility = View.VISIBLE
        handler.postDelayed(runnable, (duration / (progressBar.max / increment)).toLong())
    }
}
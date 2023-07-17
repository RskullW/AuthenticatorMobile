package com.example.kotlinauthorizationwithcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initializeButton()
    }

    private fun initializeButton() {
        val buttonLogOut = findViewById<Button>(R.id.buttonLogOut)
        val buttonRemove = findViewById<Button>(R.id.buttonRemove)

        buttonLogOut.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }

        buttonRemove.setOnClickListener {
            DataSettings.RemoveData(this)

            val intent = Intent(this, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }
    }
}
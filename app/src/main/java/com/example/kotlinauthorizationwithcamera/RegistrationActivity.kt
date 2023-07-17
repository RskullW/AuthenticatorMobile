package com.example.kotlinauthorizationwithcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initializeButton()
    }

    private fun initializeButton() {
        val editText = findViewById<EditText>(R.id.editTextPasswordReg)
        val button = findViewById<Button>(R.id.regButton)

        button.setOnClickListener {
            val text = editText.text.toString()

            if (text.length > 4 ) {
                DataSettings.SaveData(text, this)
                val intent = Intent(this, SecondActivity::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }

            else {
                Toast.makeText(this@RegistrationActivity, "The password must be more than 4 characters", Toast.LENGTH_LONG).show()
            }
        }
    }



}
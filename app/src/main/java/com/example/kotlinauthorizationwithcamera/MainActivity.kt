package com.example.kotlinauthorizationwithcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var isHaveBiometric: Boolean = true
    private lateinit var biometricPrompt: BiometricPrompt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBiometricInDevice()
        initializeButtons()

        if (isHaveBiometric) {
            initializeBiometric()
        }
    }
    private fun checkBiometricInDevice() {
        val biometricManager = BiometricManager.from(this)
        val buttonOpenCamera = findViewById<Button>(R.id.buttonOpenCamera)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                buttonOpenCamera.visibility = View.VISIBLE
                isHaveBiometric = true
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                buttonOpenCamera.visibility = View.GONE
                isHaveBiometric = false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                buttonOpenCamera.visibility = View.GONE
                isHaveBiometric = false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                buttonOpenCamera.visibility = View.GONE
                isHaveBiometric = false
            }
        }
    }
    private fun initializeButtons() {
        val logInButton = findViewById<Button>(R.id.LogIn)
        val signInButton = findViewById<Button>(R.id.SignIn)

        signInButton.setOnClickListener {
            SetActivity(RegistrationActivity::class.java, false)
        }

        logInButton.setOnClickListener {
            checkPassword()
        }
    }

    private fun checkPassword() {
        val editText = findViewById<EditText>(R.id.editTextPassword)

        if (DataSettings.isLoaded) {
            if (editText.text.toString() == DataSettings.password) {
                Toast.makeText(this@MainActivity, "Вы успешно вошли в аккаунт", Toast.LENGTH_LONG).show()
                SetActivity(SecondActivity::class.java, true)
            } else {
                Toast.makeText(this@MainActivity, "Неверный пароль", Toast.LENGTH_LONG).show()
                editText.text.clear()
            }
        }

        else {
            Toast.makeText(this@MainActivity, "Неверный пароль", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeBiometric() {
        val buttonOpenCamera = findViewById<Button>(R.id.buttonOpenCamera)

        biometricPrompt = BiometricPrompt(this@MainActivity, ContextCompat.getMainExecutor(this), object:androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                val cryptoObject = result.cryptoObject
                SetActivity(SecondActivity::class.java, true)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
        )

        buttonOpenCamera.setOnClickListener {
            biometricPrompt.authenticate(createBiometricPromptInfo())
        }
    }
    private fun <T> SetActivity(activity: Class<T>, isClearTask: Boolean) {
        val intent = Intent(this, activity)

        if (isClearTask) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        startActivity(intent)
    }
    private fun createBiometricPromptInfo(): PromptInfo {
        return PromptInfo.Builder()
            .setTitle("Authorization")
            .setNegativeButtonText("Cancel")
            .build()
    }


}
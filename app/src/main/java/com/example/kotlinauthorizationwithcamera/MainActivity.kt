package com.example.kotlinauthorizationwithcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var isHaveBiometricFace: Boolean = true
    private lateinit var biometricPrompt: BiometricPrompt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBiometricInDevice()
        initializeButtons()

        if (isHaveBiometricFace) {
            initializeBiometric()
        }
    }

    private fun checkBiometricInDevice() {
        val biometricManager = BiometricManager.from(this)
        val buttonOpenCamera = findViewById<Button>(R.id.buttonOpenCamera)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Биометрическая аутентификация доступна на устройстве
                buttonOpenCamera.visibility = View.VISIBLE
                isHaveBiometricFace = true
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // На устройстве отсутствует аппаратная поддержка биометрической аутентификации
                buttonOpenCamera.visibility = View.GONE
                isHaveBiometricFace = false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // Аппаратная поддержка биометрической аутентификации недоступна в данный момент
                buttonOpenCamera.visibility = View.GONE
                isHaveBiometricFace = false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // На устройстве не зарегистрировано ни одного биометрического шаблона
                buttonOpenCamera.visibility = View.GONE
                isHaveBiometricFace = false
            }
        }
    }
    private fun initializeButtons() {
        val logInButton = findViewById<Button>(R.id.LogIn)
        val signInButton = findViewById<Button>(R.id.SignIn)

        signInButton.setOnClickListener {
            SetActivity(RegistrationActivity::class.java)
        }

    }

    private fun initializeBiometric() {
        val buttonOpenCamera = findViewById<Button>(R.id.buttonOpenCamera)

        biometricPrompt = BiometricPrompt(this@MainActivity, ContextCompat.getMainExecutor(this), object:androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                val cryptoObject = result.cryptoObject
                SetActivity(SecondActivity::class.java)
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
    private fun <T> SetActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
    }

    private fun createBiometricPromptInfo(): PromptInfo {
        return PromptInfo.Builder()
            .setTitle("Authorization")
            .setNegativeButtonText("Cancel")
            .build()
    }


}
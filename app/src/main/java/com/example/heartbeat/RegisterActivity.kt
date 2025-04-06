package com.example.heartbeat

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.heartbeat.data.DatabaseHelper
import com.example.heartbeat.data.User
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: MaterialButton
    
    private lateinit var loginEmailInput: EditText
    private lateinit var loginPasswordInput: EditText
    private lateinit var loginButton: MaterialButton
    
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)
        
        // Initialize registration views
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        registerButton = findViewById(R.id.registerButton)
        
        // Initialize login views
        loginEmailInput = findViewById(R.id.loginEmailInput)
        loginPasswordInput = findViewById(R.id.loginPasswordInput)
        loginButton = findViewById(R.id.loginButton)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    showToast("Пожалуйста, заполните все поля")
                }
                !isValidEmail(email) -> {
                    showToast("Пожалуйста, введите корректный email")
                }
                dbHelper.isEmailExists(email) -> {
                    showToast("Этот email уже зарегистрирован")
                }
                else -> {
                    val user = User(email = email, password = password, name = email)
                    val userId = dbHelper.addUser(user)

                    if (userId != -1L) {
                        showToast("Регистрация успешна")
                        startMainActivity()
                    } else {
                        showToast("Ошибка при регистрации")
                    }
                }
            }
        }

        loginButton.setOnClickListener {
            val email = loginEmailInput.text.toString().trim()
            val password = loginPasswordInput.text.toString().trim()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    showToast("Пожалуйста, заполните все поля")
                }
                !isValidEmail(email) -> {
                    showToast("Пожалуйста, введите корректный email")
                }
                else -> {
                    val user = dbHelper.getUser(email, password)
                    if (user != null) {
                        showToast("Вход выполнен успешно")
                        startMainActivity()
                    } else {
                        showToast("Неверный email или пароль")
                    }
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
} 
package com.example.heartbeat

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var datesButton: TextView
    private lateinit var aboutButton: TextView
    private lateinit var authButton: TextView
    private lateinit var startSearchButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the default action bar
        supportActionBar?.hide()

        // Initialize views
        datesButton = findViewById(R.id.datesButton)
        aboutButton = findViewById(R.id.aboutButton)
        authButton = findViewById(R.id.authButton)
        startSearchButton = findViewById(R.id.startSearchButton)

        // Set click listeners
        datesButton.setOnClickListener {
            startActivity(Intent(this, DateIdeasActivity::class.java))
        }

        aboutButton.setOnClickListener {
            // TODO: Navigate to About screen
        }

        authButton.setOnClickListener {
            // Navigate back to RegisterActivity and finish current activity
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        startSearchButton.setOnClickListener {
            startActivity(Intent(this, DateIdeasActivity::class.java))
        }
    }
}
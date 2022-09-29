package com.cscmdd.alphabetbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val toPickerButton = findViewById<ImageButton>(R.id.toPickerButton)
        val languageSpinner = findViewById<Spinner>(R.id.languageSpinner)
        val voiceSpinner = findViewById<Spinner>(R.id.voiceSpinner)
        val speedSpinner = findViewById<Spinner>(R.id.speedSpinner)

        // toPicker page button functionality
        toPickerButton.setOnClickListener {
            val intent = Intent(this, ImagePickerActivity::class.java)
            startActivity(intent)
        }
    }
}
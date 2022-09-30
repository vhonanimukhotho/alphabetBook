package com.cscmdd.alphabetbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import java.util.Locale

class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"

        val languages = arrayOf("English-UK","English-US","Chinese","Canada-French","France-French","Germany-German","Italy-Italian","Japan-Japanese","Korea-Korean","Simplified Chinese","Traditional Chinese")
        //val languagesMap = mutableMapOf("English-Uk" to Locale.UK, "English-US" to Locale.US,"Chinese" to Locale.CHINESE,"Canada-French" to Locale.CANADA_FRENCH,"France-French" to Locale.FRENCH,"Germany-German" to Locale.GERMAN,"Italy-Italian" to Locale.ITALIAN,"Japan-Japanese" to Locale.JAPANESE,"Korea-Korean" to Locale.KOREAN,"Simplified Chinese" to Locale.SIMPLIFIED_CHINESE,"Traditional Chinese" to Locale.TRADITIONAL_CHINESE)

        val voices = arrayOf("Female","Male",)
        //val voicesMap = mutableMapOf("Female" to 1, "Male" to 2)

        val speeds = arrayOf("Slower","Slow","Normal","Fast","Faster")
        val speedsMap = mutableMapOf("Slower" to 0.1f, "Slow" to 0.5f, "Normal" to 1.0f, "Fast" to 1.5f, "Faster" to 2.0f)

        val toPickerButton = findViewById<ImageButton>(R.id.toPickerButton)
        val languageSpinner = findViewById<Spinner>(R.id.languageSpinner)
        val voiceSpinner = findViewById<Spinner>(R.id.voiceSpinner)
        val speedSpinner = findViewById<Spinner>(R.id.speedSpinner)
        val saveButton = findViewById<Button>(R.id.saveButton)

        //Language Spinner
        val languageArrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, languages)
        languageSpinner.adapter = languageArrayAdapter
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedLang = languages[p2]
                //MyGlobalVars.myLanguage = languagesMap[selectedLang]!!
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Voice spinner
        val voiceArrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, voices)
        voiceSpinner.adapter = voiceArrayAdapter
        voiceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedVoice = voices[p2]
                //MyGlobalVars.myVoice = voicesMap[selectedVoice]!!
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Speed spinner
        val speedArrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, speeds)
        speedSpinner.adapter = speedArrayAdapter
        val selectedPos = speeds.indexOf(MyGlobalVars.mySpeedKey)
        speedSpinner.setSelection(selectedPos)
        speedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedSpeed = speeds[p2]
                MyGlobalVars.mySpeed = speedsMap[selectedSpeed]!!
                MyGlobalVars.mySpeedKey = speeds[p2]!!
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        // toPicker page button functionality
        toPickerButton.setOnClickListener {
            //Toast.makeText(applicationContext,myName,Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ImagePickerActivity::class.java)
            startActivity(intent)
        }

        // Save button functionality
        saveButton.setOnClickListener {
            Toast.makeText(applicationContext,"Changes Applied!",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
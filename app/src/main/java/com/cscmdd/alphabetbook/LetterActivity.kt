package com.cscmdd.alphabetbook

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

//import android.widget.ViewSwitcher

class LetterActivity : AppCompatActivity() {


    lateinit var tts : TextToSpeech

    val images = intArrayOf(R.drawable.slide01, R.drawable.slide02, R.drawable.slide03,
        R.drawable.slide04, R.drawable.slide05, R.drawable.slide06, R.drawable.slide07,
        R.drawable.slide08, R.drawable.slide09, R.drawable.slide10, R.drawable.slide11,
        R.drawable.slide12, R.drawable.slide13, R.drawable.slide14, R.drawable.slide15,
        R.drawable.slide16, R.drawable.slide17, R.drawable.slide18, R.drawable.slide19,
        R.drawable.slide20, R.drawable.slide21, R.drawable.slide22, R.drawable.slide23,
        R.drawable.slide24, R.drawable.slide25, R.drawable.slide26)

    val letters = arrayOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
    private var position: Int = 0
    var  count :Int = 0

    @SuppressLint("SetWorldReadable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Alphabet"

        val speakButton = findViewById<ImageButton>(R.id.speakButton)
        val shareButton = findViewById<ImageButton>(R.id.shareButton)
        val downloadButton = findViewById<ImageButton>(R.id.downloadButton)
        val settingButton = findViewById<ImageButton>(R.id.settingsButton)

        val next = findViewById<ImageButton>(R.id.buttonNext)
        val prev = findViewById<ImageButton>(R.id.buttonPrev)
        val firstPage = findViewById<Button>(R.id.buttonFirst)
        val lastPage = findViewById<Button>(R.id.buttonLast)
        val overviewPage = findViewById<Button>(R.id.buttonOverview)
        val imageSwitcher = findViewById<ImageSwitcher>(R.id.imageSwitcher)

        val clickedPosition = intent.extras?.get("POSITION") as Int
        position = clickedPosition
        MyGlobalVars.currentImageIndex= position
        count = position
        imageSwitcher?.setFactory{
            val imageView = ImageView(applicationContext)
            imageView
        }

        // set the method and pass array as a parameter
        imageSwitcher?.setImageResource(images[position])

        // previous button functionality
        prev.setOnClickListener {
            position -= 1
            if(position == 0){
                imageSwitcher?.setImageResource(images[position])

                prev.setVisibility(View.INVISIBLE)
                firstPage.isEnabled = false

                next.setVisibility(View.VISIBLE)
                lastPage.isEnabled = true
            }
            else if(position in 0..25){
                imageSwitcher?.setImageResource(images[position])

                prev.setVisibility(View.VISIBLE)
                firstPage.isEnabled = true

                next.setVisibility(View.VISIBLE)
                lastPage.isEnabled = true
            }

            MyGlobalVars.currentImageIndex= position
        }
        // next button functionality
        next.setOnClickListener {
            position += 1
            if(position == images.size - 1){
                imageSwitcher?.setImageResource(images[position])

                next.setVisibility(View.INVISIBLE)
                lastPage.isEnabled = false

                prev.setVisibility(View.VISIBLE)
                firstPage.isEnabled = true
            }
            else if(position in 0..25){
                imageSwitcher?.setImageResource(images[position])

                prev.setVisibility(View.VISIBLE)
                firstPage.isEnabled = true

                next.setVisibility(View.VISIBLE)
                lastPage.isEnabled = true
            }

            MyGlobalVars.currentImageIndex= position
        }

        // first Page button functionality
        firstPage.setOnClickListener {
            position = 0
            imageSwitcher?.setImageResource(images[position])

            prev.setVisibility(View.INVISIBLE)
            firstPage.isEnabled = false

            next.setVisibility(View.VISIBLE)
            lastPage.isEnabled = true

            MyGlobalVars.currentImageIndex= position
        }

        // last Page button functionality
        lastPage.setOnClickListener {
            position = images.size - 1
            imageSwitcher?.setImageResource(images[position])

            next.setVisibility(View.INVISIBLE)
            lastPage.isEnabled = false

            prev.setVisibility(View.VISIBLE)
            firstPage.isEnabled = true

            MyGlobalVars.currentImageIndex= position
        }

        // overview Page button functionality
        overviewPage.setOnClickListener{
            val intenta = Intent(this, MainActivity::class.java)
            startActivity(intenta)
        }

        if(position==0){
            prev.setVisibility(View.INVISIBLE)
        }else{
            prev.setVisibility(View.VISIBLE)
        }
        if(position==images.size - 1){
            next.setVisibility(View.INVISIBLE)
        }else{
            next.setVisibility(View.VISIBLE)
        }

        firstPage.isEnabled = position != 0
        lastPage.isEnabled = position != images.size-1



        // speak button functionality
        speakButton.setOnClickListener{
            tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
                if(it==TextToSpeech.SUCCESS){
                    val myLanguage = MyGlobalVars.myLanguage
                    tts.language = myLanguage
                    val mySpeed = MyGlobalVars.mySpeed
                    tts.setSpeechRate(mySpeed)
                    tts.speak(letters[position], TextToSpeech.QUEUE_FLUSH, null)
                }
            })
        }

        // share image button functionality
        // image to be shared = images[position]
        shareButton.setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(resources,images[position])

            val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "tempimage", null )
            val uri = Uri.parse(path)


            val intent = Intent(Intent.ACTION_SEND)
            intent.type= ("image/*")
            intent.putExtra(Intent.EXTRA_STREAM,uri)

            // val bitmap = imgV.drawable.toBitmap()
            startActivity(Intent.createChooser(intent,"Share Image via"))
        }

        // save image button functionality
        // image to be saved = images[position]
        downloadButton.setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(resources,images[position])
            saveImage(bitmap)
        }

        // setting button functionality
        settingButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    private  fun saveImage(bitmap: Bitmap){
        val outputStream: OutputStream
        try {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
                val resolver = contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"slide"+".jpg")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"Image/jpg")
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES+ File.separator+"hid")
                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
                outputStream = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
                Objects.requireNonNull<OutputStream>(outputStream)
                Toast.makeText(this,"Image downloaded", Toast.LENGTH_SHORT).show()

            }
        }catch (e : Exception){
            Toast.makeText(this,"Image not downloaded", Toast.LENGTH_SHORT).show()

        }
    }




}
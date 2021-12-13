package com.example.roomexample

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.graphics.drawable.toBitmap
import java.lang.Exception
import android.graphics.BitmapFactory

import java.io.*


class LoginActivity : AppCompatActivity() {
    private lateinit var imgView: ImageView
    private lateinit var inputText: EditText
    private lateinit var button: Button

    private lateinit var activityResult: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imgView = findViewById(R.id.eczResim)
        inputText = findViewById(R.id.eczIsim)
        button = findViewById(R.id.login_button)

        SharedPrefManager.setup(applicationContext)

        var imgdir = SharedPrefManager.getString("profileImgDir")

        if(!imgdir.isNullOrEmpty()) {
            loadImage(imgdir)
        }

        if (SharedPrefManager.getString("eczaneIsim").isNullOrEmpty()) {
            nameChangeSetup()
        }
        else {
            initialSetup()
        }

        activityResult = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                imgView.setImageURI(uri)
                saveImage(imgView.drawable.toBitmap())
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.options_name -> {
                nameChangeSetup()
                true
            }
            R.id.options_avatar -> {
                activityResult.launch("image/*")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun nameChangeSetup() {
        button.setText(getString(R.string.save_text))
        inputText.hint = getString(R.string.change_name)
        button.setOnClickListener { OnButtonClickChangeName() }
    }

    fun initialSetup() {
        button.setText(getString(R.string.login_text))
        inputText.hint = getString(R.string.enter_input)
        button.setOnClickListener { OnButtonClickLogin() }
    }

    fun OnButtonClickChangeName() {
        var inputstr = inputText.text.toString()
        SharedPrefManager.putString("eczaneIsim", inputstr)
        initialSetup()
    }

    fun OnButtonClickLogin() {
        if(inputText.text.toString().equals(SharedPrefManager.getString("eczaneIsim"))) {
            startActivity(Intent(applicationContext, NavMenuActivity::class.java))
        }
    }

    fun saveImage(image: Bitmap) {
        var cw = ContextWrapper(applicationContext)

        var dir = cw.getDir("imageDir", Context.MODE_PRIVATE)

        var path = File(dir, "profile.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(path)
            // Use the compress method on the BitMap object to write image to the OutputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        SharedPrefManager.putString("profileImgDir", dir.absolutePath)
    }

    fun loadImage(path: String?) {
        try {
            val f: File = File(path, "profile.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            imgView.setImageBitmap(b)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}

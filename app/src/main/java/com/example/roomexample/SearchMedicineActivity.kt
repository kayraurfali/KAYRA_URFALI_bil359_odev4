package com.example.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class SearchMedicineActivity : AppCompatActivity() {

    lateinit var input : EditText
    lateinit var output : TextView

    lateinit var dao: IMedicineDataDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_medicine)

        input = findViewById(R.id.searchInput)
        output = findViewById(R.id.searchOutputMedicineLocation)

        dao = AppDatabase.getDatabase(applicationContext).medicineDataDao()
    }

    fun onSearchButtonClick(view: View) {
        val inputText = input.text.toString()
        if(inputText.isNullOrEmpty()) return

        output.text = dao.getMedicineLocationByName(inputText)
    }
}
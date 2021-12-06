package com.example.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AddNewMedicineActivity : AppCompatActivity() {

    lateinit var dao: IMedicineDataDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_medicine)

        dao = AppDatabase.getDatabase(applicationContext).medicineDataDao()
    }

    fun onClickAddNewMedicine(view: View?) {
        val medicineName = findViewById<EditText>(R.id.inputMedicineName).text.toString()
        val medicineType = findViewById<EditText>(R.id.inputMedicineType).text.toString()
        val stock = findViewById<EditText>(R.id.inputStock).text.toString()
        val companyName = findViewById<EditText>(R.id.inputCompany).text.toString()

        if(stock.toInt() < 0) {
            Toast.makeText(applicationContext, "Lütfen pozitif bir sayı giriniz!", Toast.LENGTH_SHORT).show()
            return
        }
        else if (stock.toInt() > 200) {
            Toast.makeText(applicationContext, "Eczanede aynı ilaçtan en fazla 200 adet bulunabilir!", Toast.LENGTH_SHORT).show()
            return
        }

        dao.insertMedicine(MedicineData(ilacAd = medicineName, ilacStok = stock.toInt(), ilacFirma = companyName, ilacKategori = medicineType))

        Toast.makeText(applicationContext, "İlaç başarıyla eklendi", Toast.LENGTH_SHORT).show()
    }
}
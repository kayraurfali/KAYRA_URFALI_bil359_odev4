package com.example.roomexample

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.text.isDigitsOnly
import androidx.preference.PreferenceManager


class ActivityAddNewMedicine : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var dao: IMedicineDataDao

    var companySet : HashSet<String> = HashSet()
    var categorySet : HashSet<String> = HashSet()

    var alerts : ArrayList<AlertDialog> = ArrayList()

    lateinit var selectedCompany : String
    lateinit var selectedCategory : String

    lateinit var companyDropDownList : Spinner
    lateinit var categoryDropDownList : Spinner

    var currentPopupType = -1 // 0 -> company, 1 -> category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_medicine)

        dao = AppDatabase.getDatabase(applicationContext).medicineDataDao()

        var cmpSet = SharedPrefManager.getStringSet("companySet")
        if(cmpSet != null) companySet = cmpSet as HashSet<String>

        var ctgSet = SharedPrefManager.getStringSet("categorySet")
        if(ctgSet != null) categorySet = ctgSet as HashSet<String>

        createPopup("Ekle", "Firma ekle")
        createPopup("Ekle", "İlaç türü ekle")

        companyDropDownList = findViewById(R.id.inputCompany)
        updateCompanyAdapter()

        categoryDropDownList = findViewById(R.id.inputMedicineType)
        updateCategoryAdapter()
    }

    fun onClickAddNewMedicine(view: View?) {
        val medicineName = findViewById<EditText>(R.id.inputMedicineName).text.toString()
        val medicineType = selectedCategory
        val stock = findViewById<EditText>(R.id.inputStock).text.toString()
        val companyName = selectedCompany
        val medicineLocation = findViewById<EditText>(R.id.inputMedicineLocation).text.toString()

        if(medicineName.isNullOrEmpty() || medicineType.isNullOrEmpty() || companyName.isNullOrEmpty() || stock.isNullOrEmpty() || medicineLocation.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "Lütfen seçimlerinizi kontrol ediniz.", Toast.LENGTH_SHORT).show()
            return
        }

        if(stock.toInt() < 0) {
            Toast.makeText(applicationContext, "Lütfen pozitif bir sayı giriniz!", Toast.LENGTH_SHORT).show()
            return
        }
        if (stock.toInt() > 200) {
            Toast.makeText(applicationContext, "Eczanede aynı ilaçtan en fazla 200 adet bulunabilir!", Toast.LENGTH_SHORT).show()
            return
        }
        if(!stock.isDigitsOnly()) {
            Toast.makeText(applicationContext, "Lütfen sayı giriniz!", Toast.LENGTH_SHORT).show()
            return
        }

        dao.insertMedicine(MedicineData(null, medicineName, medicineType, companyName, stock.toInt(), medicineLocation))

        Toast.makeText(applicationContext, "İlaç başarıyla eklendi", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_item_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.new_item_options_add_company -> {
                currentPopupType = 0
                alerts[currentPopupType].show()
                true
            }
            R.id.new_item_options_add_category -> {
                currentPopupType = 1
                alerts[currentPopupType].show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createPopup(title: String, msg: String) {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                var popupInput = EditText(applicationContext)
                setTitle(title)
                setView(popupInput)
                setPositiveButton(R.string.add_medicine_options_ok, DialogInterface.OnClickListener { dialog, _ ->
                    if(currentPopupType == 0) {
                        companySet.add(popupInput.text.toString())
                        SharedPrefManager.putStringSet("companySet", companySet)
                        updateCompanyAdapter()                 }
                    else if (currentPopupType == 1){
                        categorySet.add(popupInput.text.toString())
                        SharedPrefManager.putStringSet("categorySet", categorySet)
                        updateCategoryAdapter()
                    }
                    popupInput.text.clear()
                    dialog.cancel()
                })
                setNegativeButton(R.string.add_medicine_options_cancel, DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                })
            }

            // Create the AlertDialog
            builder.create()
        }
        if (alertDialog != null) {
            alerts.add(alertDialog)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        if(parent == null) return
        when(parent.id) {
            R.id.inputCompany -> selectedCompany = parent.getItemAtPosition(pos) as String
            R.id.inputMedicineType -> selectedCategory = parent.getItemAtPosition(pos) as String
            else -> return
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun updateCompanyAdapter() {
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            companySet.toList()
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        companyDropDownList.adapter = adapter
        companyDropDownList.onItemSelectedListener = this
    }

    fun updateCategoryAdapter() {
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            categorySet.toList()
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        categoryDropDownList.adapter = adapter
        categoryDropDownList.onItemSelectedListener = this
    }
}
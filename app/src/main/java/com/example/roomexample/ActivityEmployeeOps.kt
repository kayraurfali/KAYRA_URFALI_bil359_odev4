package com.example.roomexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ActivityEmployeeOps : AppCompatActivity() {

    var isAdd = true

    lateinit var employeeFirstName: TextView
    lateinit var employeeLastName: TextView

    lateinit var dao : IEmployeeDataDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_ops)

        isAdd = intent.extras!!.getBoolean("isAdd")

        dao = EmployeeDatabase.getDatabase(applicationContext).employeeDataDao()

        employeeFirstName = findViewById(R.id.inputEmployeeFirstName)
        employeeLastName = findViewById(R.id.inputEmployeeLastName)

        var button = findViewById<Button>(R.id.confirmButton)

        if(isAdd) button.text = getString(R.string.employee_add_button)
        else button.text = getString(R.string.employee_remove_button)
    }

    fun onConfirmButtonClicked(view: View) {
        if(employeeFirstName.text.toString().isNullOrEmpty() || employeeLastName.text.toString().isNullOrEmpty()) {
            Toast.makeText(applicationContext, "Lütfen tüm alanları doldurduğunuzdan emin olunuz", Toast.LENGTH_SHORT).show()
            return
        }
        var firstName = employeeFirstName.text.toString()
        var lastName = employeeLastName.text.toString()
        if(isAdd) {
            var employee = dao.loadEmployee(firstName, lastName)
            if(employee != null) {
                Toast.makeText(applicationContext, "Personel zaten kayıtlı.", Toast.LENGTH_SHORT).show()
                return
            }
            dao.insertEmployee(EmployeeData(null, firstName, lastName))
            Toast.makeText(applicationContext, "Personel başarıyla eklendi.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, ActivityEmployeeList::class.java))
        }
        else {
            var employee: EmployeeData = dao.loadEmployee(firstName, lastName)
            if(employee != null){
                dao.deleteEmployee(employee)
                Toast.makeText(applicationContext, "Personel başarıyla silindi.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, ActivityEmployeeList::class.java))
            }
            else Toast.makeText(applicationContext, "Personel veritabanında bulunamadı.", Toast.LENGTH_SHORT).show()

        }
    }
}
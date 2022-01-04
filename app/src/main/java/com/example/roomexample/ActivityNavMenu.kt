package com.example.roomexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ActivityNavMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_menu)
    }

    fun onClickNavButton(view: View) {
        when(view.id) {
            R.id.nav_menu_add_medicine_btn -> startActivity(Intent(applicationContext, ActivityAddNewMedicine::class.java))
            R.id.nav_menu_low_on_stock_btn -> startActivity(Intent(applicationContext, ActivityLowOnStock::class.java))
            R.id.nav_menu_medicine_list_btn -> startActivity(Intent(applicationContext, ActivityMedicineList::class.java))
            R.id.nav_menu_search_medicine_btn -> startActivity(Intent(applicationContext, ActivitySearchMedicine::class.java))
            R.id.nav_menu_employee_list -> startActivity(Intent(applicationContext, ActivityEmployeeList::class.java))
            else -> return
        }
    }
}
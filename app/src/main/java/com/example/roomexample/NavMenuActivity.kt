package com.example.roomexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class NavMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_menu)
    }

    fun onClickNavButton(view: View) {
        when(view.id) {
            R.id.nav_menu_add_medicine_btn -> startActivity(Intent(applicationContext, AddNewMedicineActivity::class.java))
            R.id.nav_menu_low_on_stock_btn -> startActivity(Intent(applicationContext, LowOnStockActivity::class.java))
            R.id.nav_menu_medicine_list_btn -> startActivity(Intent(applicationContext, MedicineListActivity::class.java))
            R.id.nav_menu_search_medicine_btn -> startActivity(Intent(applicationContext, SearchMedicineActivity::class.java))
            else -> return
        }
    }
}
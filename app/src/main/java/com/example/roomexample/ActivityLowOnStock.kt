package com.example.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityLowOnStock : AppCompatActivity() {

    lateinit var dao: IMedicineDataDao

    lateinit var recyclerView: RecyclerView
    lateinit var medicineListAdapter: AdapterLowOnStock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_low_on_stock)

        dao = AppDatabase.getDatabase(applicationContext).medicineDataDao()

        recyclerView = findViewById(R.id.lowOnStockMedicines)
        var arr = dao.loadLowOnStock()
        if(arr.isNotEmpty())
            medicineListAdapter = AdapterLowOnStock(arr.toList())
        else
            medicineListAdapter = AdapterLowOnStock(ArrayList())

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = medicineListAdapter

        medicineListAdapter.notifyDataSetChanged()
    }
}
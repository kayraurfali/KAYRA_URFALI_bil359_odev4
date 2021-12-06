package com.example.roomexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MedicineListActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    lateinit var ddlist: Spinner
    lateinit var dao: IMedicineDataDao

    lateinit var recyclerView: RecyclerView
    lateinit var medicineListAdapter: MedicineListAdapter

    var selectedItemPos : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilac_liste_ekrani)

        dao = AppDatabase.getDatabase(applicationContext).medicineDataDao()

        ddlist = findViewById(R.id.dropdownlist)
        ArrayAdapter.createFromResource(
            this,
            R.array.order,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            ddlist.adapter = adapter
        }
        ddlist.onItemSelectedListener = this

        recyclerView = findViewById(R.id.medicineList)
        var arr = dao.loadAllMedicines()
        if(arr.isNotEmpty())
            medicineListAdapter = MedicineListAdapter(arr.toList() as ArrayList<MedicineData>)
        else
            medicineListAdapter = MedicineListAdapter(ArrayList())

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = medicineListAdapter

        medicineListAdapter.notifyDataSetChanged()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        if(selectedItemPos == pos) return
        medicineListAdapter.resetOrderNum()
        when (pos) {
            0 -> {
                medicineListAdapter.medicineList = dao.loadAllOrderByName().toList() as ArrayList<MedicineData>
            }
            1 -> {
                medicineListAdapter.medicineList = dao.loadAllOrderByCompany().toList() as ArrayList<MedicineData>
            }
            2 -> {
                medicineListAdapter.medicineList = dao.loadAllOrderByType().toList() as ArrayList<MedicineData>

            }
        }
        medicineListAdapter.notifyDataSetChanged()
        selectedItemPos = pos
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    fun onClickNextPage(view: View?) {
        startActivity(Intent(applicationContext, ActivitySwitchVars.classes[ActivitySwitchVars.ACTIVITY_ADD_NEW_MEDICINE]))
    }
}
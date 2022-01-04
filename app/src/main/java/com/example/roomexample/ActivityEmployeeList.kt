package com.example.roomexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityEmployeeList : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    lateinit var dao: IEmployeeDataDao

    lateinit var recyclerView: RecyclerView
    lateinit var employeeListAdapter: AdapterEmployeeList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        dao = EmployeeDatabase.getDatabase(applicationContext).employeeDataDao()

        recyclerView = findViewById(R.id.employeeList)
        var arr = dao.loadAllEmployees()
        if(arr.isNotEmpty())
            employeeListAdapter = AdapterEmployeeList(arr.toList())
        else
            employeeListAdapter = AdapterEmployeeList(ArrayList())

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = employeeListAdapter

        employeeListAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.employee_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.employee_options_add -> {
                var i = Intent(applicationContext, ActivityEmployeeOps::class.java)
                i.putExtra("isAdd", true)
                startActivity(i)
                return true
            }
            R.id.employee_options_remove -> {
                var i = Intent(applicationContext, ActivityEmployeeOps::class.java)
                i.putExtra("isAdd", false)
                startActivity(i)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
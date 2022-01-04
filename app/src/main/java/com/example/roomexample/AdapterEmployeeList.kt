package com.example.roomexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterEmployeeList (var employeeList: List<EmployeeData>) : RecyclerView.Adapter<AdapterEmployeeList.ViewHolderEmployeeList>(){
    var orderNum : Int = 0

    class ViewHolderEmployeeList(view: View) : RecyclerView.ViewHolder(view)
    {
        var orderNumber : TextView = view.findViewById(R.id.orderNumber)
        var employeeInfo : TextView = view.findViewById(R.id.employeeInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEmployeeList {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_list_row, parent, false)
        return ViewHolderEmployeeList(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderEmployeeList, position: Int) {
        if(employeeList.isNullOrEmpty()) return
        var employeeData = employeeList[position]
        holder.orderNumber.text = orderNum.toString()
        holder.employeeInfo.text = employeeData.employeeFirstName + " " + employeeData.employeeLastName
        orderNum++;
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    fun resetOrderNum() {
        orderNum = 0
    }
}
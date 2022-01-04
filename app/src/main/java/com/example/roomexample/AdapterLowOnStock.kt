package com.example.roomexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterLowOnStock(var medicineList: List<MedicineData>) : RecyclerView.Adapter<AdapterLowOnStock.LowOnStockViewHolder>() {

    var orderNum : Int = 0

    class LowOnStockViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var orderNumber : TextView = view.findViewById(R.id.orderNumber)
        var medicineName : TextView = view.findViewById(R.id.lowOnStockMedicineName)
        var medicineStock : TextView = view.findViewById(R.id.medicineStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LowOnStockViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.low_on_stock_list_row, parent, false)
        return LowOnStockViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LowOnStockViewHolder, position: Int) {
        if(medicineList.isNullOrEmpty()) return
        var medicineData = medicineList[position]
        holder.orderNumber.text = orderNum.toString()
        holder.medicineName.text = medicineData.ilacAd
        holder.medicineStock.text = medicineData.ilacStok.toString()
        orderNum++
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    fun resetOrderNum() {
        orderNum = 0
    }
}
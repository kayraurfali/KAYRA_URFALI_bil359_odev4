package com.example.roomexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterMedicineList(var medicineList: List<MedicineData>) : RecyclerView.Adapter<AdapterMedicineList.MedicineViewHolder>() {

    var orderNum : Int = 0

    class MedicineViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var orderNumber : TextView = view.findViewById(R.id.orderNumber)
        var medicineName : TextView = view.findViewById(R.id.medicineName)
        var companyName : TextView = view.findViewById(R.id.medicineCompany)
        var medicineType : TextView = view.findViewById(R.id.medicineType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.medicine_list_row, parent, false)
        return MedicineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        if(medicineList.isNullOrEmpty()) return
        var medicineData = medicineList[position]
        holder.orderNumber.text = orderNum.toString()
        holder.medicineName.text = medicineData.ilacAd
        holder.companyName.text = medicineData.ilacFirma
        holder.medicineType.text = medicineData.ilacKategori
        orderNum++;
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    fun resetOrderNum() {
        orderNum = 0
    }
}
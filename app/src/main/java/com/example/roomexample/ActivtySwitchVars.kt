package com.example.roomexample

class ActivitySwitchVars {
    companion object {
        const val ACTIVITY_MEDICINE_LIST = 0
        const val ACTIVITY_ADD_NEW_MEDICINE = 1
        const val ACTIVITY_LOW_ON_STOCK = 2
        const val ACTIVITY_SEARCH_MEDICINE = 3

        var classes : ArrayList<Class<*>> = ArrayList()

        fun set() {
            classes.add(MedicineListActivity::class.java)
            classes.add(AddNewMedicineActivity::class.java)
        }
    }
}
package com.example.roomexample

import android.content.Context
import androidx.room.*


@Entity(tableName = "employeeList")
data class EmployeeData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "employee_id")
    var employeeId: Int? = null,
    @ColumnInfo(name = "employee_first_name")
    var employeeFirstName: String? = null,
    @ColumnInfo(name = "employee_last_name")
    var employeeLastName: String? = null
)

@Dao
interface IEmployeeDataDao {
    @Query("SELECT * FROM employeeList")
    fun loadAllEmployees() : Array<EmployeeData>

    @Query("SELECT * FROM employeeList WHERE employee_first_name LIKE :firstName AND employee_last_name LIKE :lastName LIMIT 1")
    fun loadEmployee(firstName: String, lastName: String) : EmployeeData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(value: EmployeeData)

    @Delete
    fun deleteEmployee(value: EmployeeData)
}

@Database(entities = [EmployeeData::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDataDao() : IEmployeeDataDao

    companion object
    {
        @Volatile
        private var Instance: EmployeeDatabase? = null

        fun getDatabase(context: Context) : EmployeeDatabase
        {
            val tempInstance = Instance
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java, "employeeList"
                )
                    .allowMainThreadQueries()
                    .build()
                Instance = instance
                return instance
            }
        }
    }
}

package com.example.roomexample

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration


@Entity(tableName = "medicines_list")
data class MedicineData (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ilac_id")
    var ilacId: Int? = null,
    @ColumnInfo(name = "ilac_ad")
    var ilacAd: String?,
    @ColumnInfo(name = "ilac_kategori")
    var ilacKategori: String?,
    @ColumnInfo(name = "ilac_firma")
    var ilacFirma: String?,
    @ColumnInfo(name = "ilac_stok")
    val ilacStok: Int
)

@Dao
interface IMedicineDataDao {
    @Query("SELECT * FROM medicines_list")
    fun loadAllMedicines() : Array<MedicineData>

    @Query("SELECT * FROM medicines_list ORDER BY ilac_ad ASC")
    fun loadAllOrderByName() : Array<MedicineData>

    @Query("SELECT * FROM medicines_list ORDER BY ilac_firma ASC")
    fun loadAllOrderByCompany() : Array<MedicineData>

    @Query("SELECT * FROM medicines_list ORDER BY ilac_kategori ASC")
    fun loadAllOrderByType() : Array<MedicineData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicine(value: MedicineData)
}

@Database(entities = [MedicineData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDataDao(): IMedicineDataDao

    companion object
    {
        @Volatile
        private var Instance : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase
        {
            val tempInstance = Instance
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "medicines_list"
                )
                    .allowMainThreadQueries()
                    .build()
                Instance = instance
                return instance
            }
        }
    }
}
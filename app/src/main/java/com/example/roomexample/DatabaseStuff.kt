package com.example.roomexample

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration


@Entity(tableName = "medicineList")
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
    var ilacStok: Int,
    @ColumnInfo(name = "ilac_konum")
    var ilacKonum: String?
)

@Dao
interface IMedicineDataDao {
    @Query("SELECT * FROM medicineList")
    fun loadAllMedicines() : Array<MedicineData>

    @Query("SELECT * FROM medicineList ORDER BY ilac_ad ASC")
    fun loadAllOrderByName() : Array<MedicineData>

    @Query("SELECT * FROM medicineList ORDER BY ilac_firma ASC")
    fun loadAllOrderByCompany() : Array<MedicineData>

    @Query("SELECT * FROM medicineList ORDER BY ilac_kategori ASC")
    fun loadAllOrderByType() : Array<MedicineData>

    @Query("SELECT * FROM medicineList WHERE ilac_stok < 20 ORDER BY ilac_stok ASC ")
    fun loadLowOnStock() : Array<MedicineData>

    @Query("SELECT ilac_konum FROM medicineList WHERE ilac_ad LIKE :medicineName LIMIT 1")
    fun getMedicineLocationByName(medicineName: String) : String

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
                    AppDatabase::class.java, "medicineList"
                )
                    .allowMainThreadQueries()
                    .build()
                Instance = instance
                return instance
            }
        }
    }
}
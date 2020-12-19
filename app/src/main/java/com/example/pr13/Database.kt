package com.example.pr13

import androidx.room.*

@Entity
data class CountryDB (
    @ColumnInfo(name="name") val name: String?,
    @ColumnInfo(name="region") val region: String?,
    @ColumnInfo(name="subregion") val subregion: String?,
    @ColumnInfo(name="capital") val capital: String?,
    @ColumnInfo(name="population") val population:Int? ) { @PrimaryKey(autoGenerate = true) var uid:Int = 0 }

@Dao
interface CountryDao {
    @Query("SELECT * FROM CountryDB")
    fun getAll():List<CountryDB>

    @Query("DELETE FROM CountryDB")
    fun clear()

    @Insert
    fun insert(countryDB: List<CountryDB>)

}

@Database(entities = [CountryDB::class],version = 1)
abstract class CountryDatabase:RoomDatabase() {
    abstract fun countryDao(): CountryDao
}
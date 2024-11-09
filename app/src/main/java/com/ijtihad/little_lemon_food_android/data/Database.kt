package com.bush.littlelemoncappro.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [MenuItem::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}

@Entity
data class MenuItem (
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)

@Dao
interface MenuItemDao {
    @Insert
    fun insertAll (menuItems: List<MenuItem>)

    @Query("SELECT * FROM MENUITEM")
    fun getAll(): LiveData<List<MenuItem>>

    @Query("SELECT (SELECT COUNT(*) FROM MenuItem) == 0")
    fun isEmpty(): Boolean

    @Query("DELETE FROM MenuItem")
    fun deleteAll()
}
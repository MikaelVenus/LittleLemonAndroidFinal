package com.example.littlelemonfinalnc.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

// 1. The Entity (The Database Table)
@Entity
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String
)

// 2. The DAO (Data Access Object - Your SQL Queries)
@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemRoom")
    fun getAll(): LiveData<List<MenuItemRoom>>

    @Insert
    fun insertAll(menuItems: List<MenuItemRoom>)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemRoom) == 0")
    fun isEmpty(): Boolean

    @Query("DELETE FROM MenuItemRoom")
    fun deleteAll()
}

// 3. The Database Setup
@Database(entities = [MenuItemRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}

// 4. Singleton pattern to ensure we only open one database connection
object MenuDatabase {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "database"
            ).build().also { instance = it }
        }
    }
}
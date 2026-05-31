package com.example.myapplication.data.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow
class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}
@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int
)
@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val total: Double,
    val customerName: String,
    val itemsCount: Int
)
@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAll(): Flow<List<CartItemEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItemEntity)
    @Delete
    suspend fun delete(item: CartItemEntity)
    @Query("DELETE FROM cart_items")
    suspend fun clearAll()
    @Query("SELECT * FROM cart_items WHERE productId = :id")
    suspend fun getById(id: Int): CartItemEntity?
}
@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY date DESC")
    fun getAll(): Flow<List<OrderEntity>>
    @Insert
    suspend fun insert(order: OrderEntity): Long
}
@Database(entities = [CartItemEntity::class, OrderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    // Débutant : Companion object pour singleton (pas de Hilt)
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "market_database"
                )
                    .fallbackToDestructiveMigration() 
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

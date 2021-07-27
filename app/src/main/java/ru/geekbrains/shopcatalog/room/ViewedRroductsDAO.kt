package ru.geekbrains.shopcatalog.room

import androidx.room.*

@Dao
interface ViewedRroductsDAO {

    @Query("SELECT * FROM ViewedProductsEntity")
    fun all(): List<ViewedProductsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: ViewedProductsEntity)

    @Update
    fun update(entity: ViewedProductsEntity)

    @Delete
    fun delete(entity: ViewedProductsEntity)
}
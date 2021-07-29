package ru.geekbrains.shopcatalog.room

import androidx.room.*

@Dao
interface ViewedProductsDAO {

    @Query("SELECT * FROM ViewedProductsEntity")
    fun detAllViewedProduct(): List<ViewedProductsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertViewedProduct(entity: ViewedProductsEntity)

    @Update
    fun updateViewedProduct(entity: ViewedProductsEntity)

    @Delete
    fun deleteViewedProduct(entity: ViewedProductsEntity)
}
package ru.geekbrains.shopcatalog.localdata.dao

import androidx.room.*
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity

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
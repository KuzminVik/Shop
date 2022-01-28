package ru.geekbrains.shopcatalog.localdata.dao

import androidx.room.*
import ru.geekbrains.shopcatalog.localdata.entity.FavoriteEntity
import ru.geekbrains.shopcatalog.localdata.entity.ViewedProductsEntity

@Dao
interface ViewedProductsDAO {

    @Query("SELECT * FROM ViewedProductsEntity")
    fun getAllViewedProduct(): List<ViewedProductsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertViewedProduct(entity: ViewedProductsEntity)

    @Delete
    fun deleteViewedProduct(entity: ViewedProductsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteProduct(entity: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    fun getAllFavorites(): List<FavoriteEntity>

    @Query("DELETE FROM FavoriteEntity WHERE id = :id" )
    fun deleteFavoriteEntity(id: Long)
}
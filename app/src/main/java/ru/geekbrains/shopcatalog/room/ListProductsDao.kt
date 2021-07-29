package ru.geekbrains.shopcatalog.room

import androidx.room.*

@Dao
interface ListProductsDao {

    @Transaction
    @Query("SELECT * FROM ProductEntity")
    fun getProductsInCategoryWithName(name: String): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id_product IS :id")
    fun getProductWithId(id: String): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE name IS :name")
    fun getProductWithName(name: String): ProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListProducts(list: List<ProductEntity>)

    @Delete
    fun deleteProduct(product: ProductEntity)

    @Delete
    fun deleteListProducts(list: List<ProductEntity>)

    @Update
    fun updateProduct(product: ProductEntity)

    @Update
    fun updateListProducts(list: List<ProductEntity>)

}
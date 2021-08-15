package ru.geekbrains.shopcatalog.localdata.dao

import androidx.room.*
import ru.geekbrains.shopcatalog.localdata.entity.ProductEntity

@Dao
interface ListProductsDAO {

    @Query("SELECT * FROM ProductEntity WHERE categoryId LIKE :name")
    suspend fun getListProductsWithNameCategory(name: String): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id_product = :id")
    fun getProductWithId(id: String): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE name = :name")
    suspend fun getProductWithName(name: String): ProductEntity

    @Query("SELECT * FROM ProductEntity WHERE id_product IN (:list)")
    fun getAllViewedProduct(list: List<String>): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListProducts(list: List<ProductEntity>)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Delete
    suspend fun deleteListProducts(list: List<ProductEntity>)

    @Query("DELETE FROM ProductEntity WHERE name = :nameCategory" )
    suspend fun deleteListProductsInCategory(nameCategory: String)

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAllProducts()

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Update
    suspend fun updateListProducts(list: List<ProductEntity>)

}
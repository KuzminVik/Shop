package ru.geekbrains.shopcatalog.localdata.dao

import androidx.room.*
import ru.geekbrains.shopcatalog.apidata.ProductDTO
import ru.geekbrains.shopcatalog.model.Product
import ru.geekbrains.shopcatalog.room.ProductEntity

@Dao
interface ListProductsDAO {

    @Transaction
    @Query("SELECT * FROM ProductEntity WHERE name IS :name")
    suspend fun getProductsInCategoryWithName(name: String): List<Product>

    @Query("SELECT * FROM ProductEntity WHERE id_product IS :id")
    suspend fun getProductWithId(id: String): Product

    @Query("SELECT * FROM ProductEntity WHERE name IS :name")
    suspend fun getProductWithName(name: String): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListProducts(list: List<ProductEntity>)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Delete
    suspend fun deleteListProducts(list: List<ProductEntity>)

    @Query("DELETE FROM ProductEntity WHERE name IS :nameCategory" )
    suspend fun deleteListProductsInCategory(nameCategory: String)

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAllProducts()

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Update
    suspend fun updateListProducts(list: List<ProductEntity>)

}
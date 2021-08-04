package ru.geekbrains.shopcatalog.localdata.dao

import androidx.room.*
import ru.geekbrains.shopcatalog.room.CategoryEntity


@Dao
interface ListCategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCategory(list: List<CategoryEntity>)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Update
    suspend fun updateListCategory(list: List<CategoryEntity>)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("DELETE FROM CategoryEntity")
    suspend fun deleteAllCategory()

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getAllCategory(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE id_category IS :id")
    suspend fun getCategoryWithId(id: String): CategoryEntity

    @Query("SELECT * FROM CategoryEntity WHERE name IS :name")
    suspend fun getCategoryWithName(name: String): CategoryEntity
}
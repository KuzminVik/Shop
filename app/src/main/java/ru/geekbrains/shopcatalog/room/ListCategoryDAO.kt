package ru.geekbrains.shopcatalog.room

import androidx.room.*


@Dao
interface ListCategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListCategory(list: List<CategoryEntity>)

    @Update
    fun updateCategory(category: CategoryEntity)

    @Update
    fun updateListCategory(list: List<CategoryEntity>)

    @Delete
    fun deleteCategory(category: CategoryEntity)

    @Delete
    fun deleteListCategory(list: List<CategoryEntity>)

    @Query("SELECT * FROM CategoryEntity")
    fun getAllCategory(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE id_category IS :id")
    fun getCategoryWithId(id: String): ProductEntity

    @Query("SELECT * FROM CategoryEntity WHERE name IS :name")
    fun getCategoryWithName(name: String): ProductEntity
}
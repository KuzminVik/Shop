package ru.geekbrains.shopcatalog.room

import androidx.room.*

@Dao
interface ViewedRroductsDAO {

    @Query("SELECT * FROM ViewedRroductsEntity")
    fun all(): List<ViewedRroductsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: ViewedRroductsEntity)

    @Update
    fun update(entity: ViewedRroductsEntity)

    @Delete
    fun delete(entity: ViewedRroductsEntity)
}
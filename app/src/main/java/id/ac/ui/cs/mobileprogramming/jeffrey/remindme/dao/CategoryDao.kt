package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table order by categoryId DESC")
    fun getCategories(): LiveData<List<Category>>

    @Insert
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Query( "SELECT name FROM category_table")
    fun getNameList(): LiveData<List<String>>
}
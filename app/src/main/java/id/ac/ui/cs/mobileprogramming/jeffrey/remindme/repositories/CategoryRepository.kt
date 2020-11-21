package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.TodoDatabase
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao.CategoryDao
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CategoryRepository(application: Application) {

    private val categoryDao: CategoryDao
    private val allCategories: LiveData<List<Category>>

    init {
        val database = TodoDatabase.getInstance(application.applicationContext)
        categoryDao = database!!.categoryDao()
        allCategories = categoryDao.getCategories()
    }

    fun insertCategory(category: Category) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                categoryDao.insertCategory(category)
            }
        }
    }

    fun deleteCategory(category: Category) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                categoryDao.deleteCategory(category)
            }
        }
    }

    fun updateCategory(category: Category) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                categoryDao.updateCategory(category)
            }
        }
    }

    fun getNameList() : LiveData<List<String>> {
        return categoryDao.getNameList()
    }

    fun getCategories(): LiveData<List<Category>> {
        return allCategories
    }
}
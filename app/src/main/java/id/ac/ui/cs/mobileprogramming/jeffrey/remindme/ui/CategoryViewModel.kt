package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Category
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.repositories.CategoryRepository

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoryRepository = CategoryRepository(application)
    private val allCategoriesList: LiveData<List<Category>> = repository.getCategories()
    private val mSpinnerData: LiveData<List<String>> = repository.getNameList()

    fun insertCategory(category: Category) {
        repository.insertCategory(category)
    }

    fun updateCategory(category: Category) {
        repository.updateCategory(category)
    }

    fun deleteCategory(category: Category) {
        repository.deleteCategory(category)
    }

    fun getCategories(): LiveData<List<Category>> {
        return allCategoriesList
    }

    fun fetchCategoryItems(): LiveData<List<String>> {
        return mSpinnerData
    }

}
package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.GLActivity
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.R
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Category
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants
import kotlinx.android.synthetic.main.activity_category_list.*

class CategoryListActivity : AppCompatActivity(), CategoryListAdapter.CategoryEvents {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var searchView: SearchView
    private lateinit var categoryAdapter: CategoryListAdapter

    init {
        System.loadLibrary("native-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        //Bottom Navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.category_nav

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.todo_nav -> {
                    startActivity(Intent(applicationContext, TodoListActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.category_nav -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.opengl_nav -> {
                    startActivity(Intent(applicationContext, GLActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        //Setting up RecyclerView
        category_list.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryListAdapter(this)
        category_list.adapter = categoryAdapter

        //Setting up ViewModel and LiveData
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel.getCategories().observe(this, Observer {
            categoryAdapter.setAllCategoryItems(it)
        })

        //Add New Category Button
        button_new_category.setOnClickListener {
            if (!searchView.isIconified) {
                searchView.isIconified = true
            }
            val intent = Intent(this@CategoryListActivity, CreateCategoryActivity::class.java)
            startActivityForResult(intent, Constants.INTENT_CREATE_CATEGORY)
        }
    }

    //Delete when delete button clicked
    override fun onDeleteClicked(category: Category) {
        categoryViewModel.deleteCategory(category)
    }

    //Search when search button clicked
    override fun onViewClicked(category: Category) {
        if (!searchView.isIconified) {
            searchView.isIconified = true
        }
        val intent = Intent(baseContext, CreateCategoryActivity::class.java)
        intent.putExtra(Constants.INTENT_OBJECT, category)
        startActivityForResult(intent, Constants.INTENT_UPDATE_CATEGORY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val category = data?.getParcelableExtra<Category>(Constants.INTENT_OBJECT)!!
            when (requestCode) {
                Constants.INTENT_CREATE_CATEGORY -> {
                    if (haveNetwork()) {
                        categoryViewModel.insertCategory(category)
                    } else if (!haveNetwork()) {
                        Toast.makeText(
                            this,
                            errorFromJNI(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                Constants.INTENT_UPDATE_CATEGORY -> {
                    if (haveNetwork()) {
                        categoryViewModel.updateCategory(category)
                    } else if (!haveNetwork()) {
                        Toast.makeText(
                            this,
                            errorFromJNI(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun haveNetwork(): Boolean {
        var haveWIFI = false
        var haveMobileData = false
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (info in networkInfos) {
            if (info.typeName.equals("WIFI", ignoreCase = true)) if (info.isConnected) haveWIFI =
                true
            if (info.typeName.equals(
                    "MOBILE DATA",
                    ignoreCase = true
                )
            ) if (info.isConnected) haveMobileData = true
        }
        return haveWIFI || haveMobileData
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.search_todo)
            ?.actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                categoryAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                categoryAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.search_todo -> true
            else -> item?.let { super.onOptionsItemSelected(it) }
        }
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
        }
        super.onBackPressed()
    }

    private external fun errorFromJNI(): String

}
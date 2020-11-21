package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.R
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Todo
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.content_main.*

class TodoListActivity : AppCompatActivity(), TodoListAdapter.TodoEvents {

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var searchView: SearchView
    private lateinit var todoAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        // Bottom Navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.todo_nav

        bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.todo_nav -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.category_nav -> {
                    startActivity( Intent(applicationContext, CategoryListActivity::class.java) )
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        todo_list.layoutManager = LinearLayoutManager(this)
        todoAdapter = TodoListAdapter(this)
        todo_list.adapter = todoAdapter

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        todoViewModel.getTodos().observe(this, Observer {
            todoAdapter.setAllTodoItems(it)
        })

        button_new_todo.setOnClickListener {
            if (!searchView.isIconified) {
                searchView.isIconified = true
            }
            val intent = Intent(this@TodoListActivity, CreateTodoActivity::class.java)
            startActivityForResult(intent, Constants.INTENT_CREATE_TODO)
        }
    }

    override fun onDeleteClicked(todo: Todo) {
        todoViewModel.deleteTodo(todo)
    }

    override fun onViewClicked(todo: Todo) {
        if (!searchView.isIconified) {
            searchView.isIconified = true
        }
        val intent = Intent(baseContext, CreateTodoActivity::class.java)
        intent.putExtra(Constants.INTENT_OBJECT, todo)
        startActivityForResult(intent, Constants.INTENT_UPDATE_TODO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val todo = data?.getParcelableExtra<Todo>(Constants.INTENT_OBJECT)!!
            when (requestCode) {
                Constants.INTENT_CREATE_TODO -> {
                    todoViewModel.insertTodo(todo)
                }
                Constants.INTENT_UPDATE_TODO -> {
                    todoViewModel.updateTodo(todo)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.search_todo)
            ?.actionView as SearchView
        searchView.setSearchableInfo(searchManager
            .getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                todoAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                todoAdapter.filter.filter(newText)
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

}
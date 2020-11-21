package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.TodoDatabase
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TodoRepository(application: Application) {

    private val todoDao: TodoDao
    private val allTodos: LiveData<List<Todo>>

    init {
        val database = TodoDatabase.getInstance(application.applicationContext)
        todoDao = database!!.todoDao()
        allTodos = todoDao.getTodos()
    }

    fun insertTodo(todo: Todo) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                todoDao.insertTodo(todo)
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                todoDao.deleteTodo(todo)
            }
        }
    }

    fun updateTodo(todo: Todo) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                todoDao.updateTodo(todo)
            }
        }
    }

    fun getTodos(): LiveData<List<Todo>> {
        return allTodos
    }

}
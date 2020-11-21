package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Todo
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.repositories.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository = TodoRepository(application)
    private val allTodoList: LiveData<List<Todo>> = repository.getTodos()

    fun insertTodo(todo: Todo) {
        repository.insertTodo(todo)
    }

    fun updateTodo(todo: Todo){
        repository.updateTodo(todo)
    }

    fun deleteTodo(todo: Todo) {
        repository.deleteTodo(todo)
    }

    fun getTodos(): LiveData<List<Todo>> {
        return allTodoList
    }

}
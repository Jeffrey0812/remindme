package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table order by date DESC, time ASC")
    fun getTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table where todoId = :todoId")
    fun getTodo(todoId: Int): LiveData<Todo>

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

}
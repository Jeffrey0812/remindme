package id.ac.ui.cs.mobileprogramming.jeffrey.remindme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao.CategoryDao
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao.TodoDao
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao.UserDao
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Category
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Todo
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.User

@Database(entities = [Todo::class, User::class, Category::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase? {
            if (INSTANCE == null) {
                synchronized(TodoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        TodoDatabase::class.java,
                        "todo_db")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
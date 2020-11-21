package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.TodoDatabase
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao.UserDao
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.User

class UserRepository(application: Application) {

    private val userDao: UserDao
    private val allUsers: LiveData<List<User>>

    init {
        val database = TodoDatabase.getInstance(application.applicationContext)
        userDao = database!!.userDao()
        allUsers = userDao.getUsers()
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun getUsers(): LiveData<List<User>> {
        return allUsers
    }

}
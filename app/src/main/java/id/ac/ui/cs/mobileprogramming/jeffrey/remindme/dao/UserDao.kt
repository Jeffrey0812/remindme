package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table order by userId DESC")
    fun getUsers(): LiveData<List<User>>

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}
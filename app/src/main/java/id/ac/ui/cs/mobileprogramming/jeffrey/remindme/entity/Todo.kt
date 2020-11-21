package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class Todo (
    @PrimaryKey(autoGenerate = true) val todoId: Int,

    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "image") val image: String?,

    @ColumnInfo(name = "userTodoId") val userTodoId: Int,
    @ColumnInfo(name = "catTodoId") val catTodoId: Int
) : Parcelable
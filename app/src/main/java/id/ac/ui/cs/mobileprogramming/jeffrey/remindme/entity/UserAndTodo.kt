package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndTodo(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userTodoId"
    )
    val todo: Todo
)
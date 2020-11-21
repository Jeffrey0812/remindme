package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryAndTodo(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "catTodoId"
    )
    val todo: Todo
)
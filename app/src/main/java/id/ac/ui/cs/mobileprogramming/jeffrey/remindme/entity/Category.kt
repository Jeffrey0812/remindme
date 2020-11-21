package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "category_table")
@Parcelize
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Int,

    @ColumnInfo(name = "name") val name: String,
): Parcelable


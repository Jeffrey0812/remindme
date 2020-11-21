package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User (
    @PrimaryKey(autoGenerate = true) val userId: Int,

    @ColumnInfo(name = "name") val title: String
) : Parcelable
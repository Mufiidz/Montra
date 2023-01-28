package com.mufidz.montra.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Report(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") var title: String? = "",
    @ColumnInfo(name = "created_time") var createdTime: Long = 0,
    @ColumnInfo(name = "updated_time") var updatedTime: Long = 0,
    @ColumnInfo(name = "amount") var amount: Int = 0,
    @ColumnInfo(name = "isIncome") var isIncome: Boolean = true,
    @ColumnInfo(name = "tag") var tag: String? = "",
    @ColumnInfo(name = "comment") var comment: String? = "",
    @ColumnInfo(name = "isSecret") var isSecret: Boolean = false
) : Parcelable {

    fun isSecretToInt(): Int = if (isSecret) 1 else 0
}

package br.com.charleston.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "make")
data class MakeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)
package br.com.charleston.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle")
data class VehicleEntity(
    @PrimaryKey
    val id: Int,
    val make: String,
    val model: String,
    val version: String,
    val image: String,
    val km: Int,
    val price: String,
    val yearModel: String,
    val yearFab: String,
    val color: String,
    val isFavorite: Boolean? = false
)
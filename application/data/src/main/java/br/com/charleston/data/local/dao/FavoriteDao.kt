package br.com.charleston.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.charleston.data.local.entity.FavoriteEntity
import br.com.charleston.data.local.entity.VehicleEntity

interface FavoriteDao {

    @Query("SELECT vehicle.* FROM favorite INNER JOIN vehicle ON vehicle.id = favorite.vehicleId")
    fun findAll(): List<VehicleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(favoriteEntity: FavoriteEntity)
}
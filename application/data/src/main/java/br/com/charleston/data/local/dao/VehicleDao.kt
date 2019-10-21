package br.com.charleston.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.charleston.data.local.entity.VehicleEntity

@Dao
interface VehicleDao {

    @Query("SELECT * FROM vehicle")
    fun findAll(): List<VehicleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(favoriteEntity: VehicleEntity)
}
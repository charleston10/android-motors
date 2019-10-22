package br.com.charleston.data.local.dao

import androidx.room.*
import br.com.charleston.data.local.entity.FavoriteEntity
import br.com.charleston.data.local.entity.VehicleEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavoriteDao {

    @Query("SELECT vehicle.* FROM favorite INNER JOIN vehicle ON vehicle.id = favorite.vehicleId")
    fun findAll(): Observable<List<VehicleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE vehicleId = :vehicleId")
    fun remove(vehicleId: Int)
}
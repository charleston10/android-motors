package br.com.charleston.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.charleston.data.local.entity.VehicleEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface VehicleDao {

    @Query("SELECT *, (SELECT COUNT(favorite.vehicleId) > 0 FROM favorite WHERE favorite.vehicleId = vehicleId) AS isFavorite FROM vehicle")
    fun findAll(): Observable<List<VehicleEntity>>

    @Query("SELECT * FROM vehicle WHERE id= :vehicleId")
    fun findById(vehicleId: Int): Observable<VehicleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(list: List<VehicleEntity>)

    @Query("SELECT * FROM vehicle WHERE isFavorite = 1")
    fun findByFavorite(): Observable<List<VehicleEntity>>

    @Query("UPDATE vehicle SET isFavorite = :isFavorite WHERE id = :vehicleId")
    fun updateFavorite(isFavorite: Int, vehicleId: Int): Int
}
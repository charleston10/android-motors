package br.com.charleston.data.local.dao

import androidx.room.*
import br.com.charleston.data.local.entity.FavoriteEntity
import br.com.charleston.data.local.entity.MakeEntity
import br.com.charleston.data.local.entity.VehicleEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MakeDao {

    @Query("SELECT * FROM make")
    fun findAll(): Observable<List<MakeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(makeEntity: List<MakeEntity>)
}
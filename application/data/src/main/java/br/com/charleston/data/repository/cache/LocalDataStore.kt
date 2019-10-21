package br.com.charleston.data.repository.cache

import br.com.charleston.data.local.cache.FavoriteCache
import br.com.charleston.data.local.cache.VehicleCache
import br.com.charleston.data.local.entity.FavoriteEntity
import br.com.charleston.data.local.entity.VehicleEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocalDataStore @Inject constructor(
    private val favoriteCache: FavoriteCache,
    private val vehicleCache: VehicleCache
) {

    fun favorite(vehicleId: Int): Completable {
        return favoriteCache.save(
            entity = FavoriteEntity(
                vehicleId = vehicleId
            )
        )
    }

    fun findFavorites(): Observable<List<VehicleEntity>> {
        return favoriteCache.findAll()
    }

    fun findVehicles(): Observable<List<VehicleEntity>> {
        return vehicleCache.findAll()
    }

    fun insertVehicle(vehicleEntity: VehicleEntity) {
        vehicleCache.save(vehicleEntity)
    }
}
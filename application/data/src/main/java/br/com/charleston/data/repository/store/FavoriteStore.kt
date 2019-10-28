package br.com.charleston.data.repository.store

import br.com.charleston.data.local.cache.FavoriteCache
import br.com.charleston.data.local.entity.VehicleEntity
import io.reactivex.Observable
import javax.inject.Inject

class FavoriteStore @Inject constructor(
    private val cache: FavoriteCache
) {

    fun favorite(vehicleId: Int) {
        cache.favorite(vehicleId)
    }

    fun removeFavorite(vehicleId: Int) {
        cache.disfavor(vehicleId)
    }

    fun findFavorites(): Observable<List<VehicleEntity>> {
        return cache.findByFavorite()
    }
}
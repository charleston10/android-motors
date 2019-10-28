package br.com.charleston.data.local.cache

import android.content.Context
import br.com.charleston.data.local.AppDatabase
import br.com.charleston.data.local.entity.FavoriteEntity
import br.com.charleston.data.local.entity.VehicleEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FavoriteCache @Inject constructor(
    private val context: Context
) {

    private val dao by lazy { AppDatabase.getInstance(context).vehicleDao() }

    fun findByFavorite(): Observable<List<VehicleEntity>> {
        return dao.findByFavorite()
    }

    fun favorite(vehicleId: Int) {
        dao.updateFavorite(1, vehicleId)
    }

    fun disfavor(vehicleId: Int) {
        dao.updateFavorite(0, vehicleId)
    }
}
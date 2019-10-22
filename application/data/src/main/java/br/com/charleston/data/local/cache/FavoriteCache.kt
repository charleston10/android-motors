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

    private val dao by lazy { AppDatabase.getInstance(context).favoriteDao() }

    fun save(entity: FavoriteEntity) {
        dao.save(entity)
    }

    fun findAll(): Observable<List<VehicleEntity>> {
        return dao.findAll()
    }
}
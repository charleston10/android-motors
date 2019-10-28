package br.com.charleston.data.local.cache

import android.content.Context
import br.com.charleston.data.local.AppDatabase
import br.com.charleston.data.local.entity.MakeEntity
import io.reactivex.Observable
import javax.inject.Inject

class MakeCache @Inject constructor(
    private val context: Context
) {

    private val dao by lazy { AppDatabase.getInstance(context).makeDao() }

    fun save(entity: List<MakeEntity>) {
        dao.save(entity)
    }

    fun findAll(): Observable<List<MakeEntity>> {
        return dao.findAll()
    }
}
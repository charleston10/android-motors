package br.com.charleston.data.repository.store

import br.com.charleston.data.local.cache.MakeCache
import br.com.charleston.data.local.entity.MakeEntity
import io.reactivex.Observable
import javax.inject.Inject

class MakeStore @Inject constructor(
    private val cache: MakeCache
) {

    fun save(makeEntity: List<MakeEntity>) {
        cache.save(makeEntity)
    }


    fun findAll(): Observable<List<MakeEntity>> {
        return cache.findAll()
    }
}
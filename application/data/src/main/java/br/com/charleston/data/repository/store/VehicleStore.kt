package br.com.charleston.data.repository.store

import br.com.charleston.data.local.cache.VehicleCache
import br.com.charleston.data.local.entity.VehicleEntity
import io.reactivex.Observable
import javax.inject.Inject

class VehicleStore @Inject constructor(
    private val vehicleCache: VehicleCache
) {

    fun findAll(): Observable<List<VehicleEntity>> {
        return vehicleCache.findAll()
    }

    fun insertList(vehicleEntity: List<VehicleEntity>) {
        vehicleCache.save(vehicleEntity)
    }
}
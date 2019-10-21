package br.com.charleston.data.local.cache

import android.content.Context
import br.com.charleston.data.local.AppDatabase
import br.com.charleston.data.local.entity.VehicleEntity
import javax.inject.Inject

class VehicleCache @Inject constructor(
    private val context: Context
) {

    private val dao by lazy { AppDatabase.getInstance(context).vehicleDao() }

    fun save(entity: VehicleEntity){
        dao.save(entity)
    }

    fun findAll(): List<VehicleEntity> {
        return dao.findAll()
    }
}
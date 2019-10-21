package br.com.charleston.data.repository

import br.com.charleston.data.repository.cache.LocalDataStore
import br.com.charleston.data.repository.cloud.CloudDataStore
import br.com.charleston.data.repository.mappers.*
import br.com.charleston.domain.model.*
import br.com.charleston.domain.repository.IAppRepository
import io.reactivex.Completable
import io.reactivex.Observable

class AppDataRepository(
    private val cloud: CloudDataStore,
    private val local: LocalDataStore
) : IAppRepository {

    override fun getMakes(): Observable<List<MakeModel>> {
        val mapper = MakeResponseToModelMapper()

        return cloud.getMakes()
            .map {
                mapper.transform(it)
            }
    }

    override fun getModel(makeId: String): Observable<List<Model>> {
        val mapper = ModelResponseToModelMapper()

        return cloud.getModel(makeId)
            .map {
                mapper.transform(it)
            }
    }

    override fun getVersion(modelId: String): Observable<List<VersionModel>> {
        val mapper = VersionResponseToModelMapper()

        return cloud.getVersion(modelId)
            .map {
                mapper.transform(it)
            }
    }

    override fun getVehicles(page: Int): Observable<List<VehicleModel>> {
        val mapper = VehicleResponseToModelMapper()

        return cloud.getVehicles(page)
            .map {
                mapper.transform(it)
            }
    }

    override fun favorite(vehicleId: Int): Completable {
        return local.favorite(vehicleId)
    }

    override fun getFavorites(): Observable<List<VehicleModel>> {
        val mapper = VehicleEntityToModelMapper()
        
        return local.findFavorites()
            .map { mapper.transform(it) }
    }
}
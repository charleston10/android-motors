package br.com.charleston.data.repository

import br.com.charleston.data.repository.cloud.CloudDataStore
import br.com.charleston.data.repository.mappers.*
import br.com.charleston.data.repository.store.FavoriteStore
import br.com.charleston.data.repository.store.MakeStore
import br.com.charleston.data.repository.store.VehicleStore
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.Model
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.domain.model.VersionModel
import br.com.charleston.domain.repository.IAppRepository
import io.reactivex.Observable
import io.reactivex.Single

class AppDataRepository(
    private val cloud: CloudDataStore,
    private val vehicleStore: VehicleStore,
    private val favoriteStore: FavoriteStore,
    private val makeStore: MakeStore
) : IAppRepository {

    override fun getMakes(): Observable<List<MakeModel>> {
        val mapperResponseToEntity = MakeResponseToEntityMapper()
        val mapperEntityToModel = MakeEntityToModelMapper()

        return cloud.getMakes()
            .map {
                mapperResponseToEntity.transform(it)
            }
            .doOnNext {
                makeStore.save(it)
            }
            .map {
                mapperEntityToModel.transform(it)
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
        val mapperResponseToEntity = VehicleResponseToEntitylMapper()
        val mapperEntityToModel = VehicleEntityToModelMapper()

        return cloud.getVehicles(page)
            .retry(3)
            .map {
                mapperResponseToEntity.transform(it)
            }
            .doOnNext {
                vehicleStore.insertList(it)
            }
            .map {
                mapperEntityToModel.transform(it)
            }
    }

    override fun favorite(vehicleId: Int) {
        favoriteStore.favorite(vehicleId)
    }

    override fun getFavorites(): Observable<List<VehicleModel>> {
        val mapper = VehicleEntityToModelMapper()

        return favoriteStore.findFavorites()
            .map { mapper.transform(it) }
    }

    override fun removeFavorite(vehicleId: Int) {
        favoriteStore.removeFavorite(vehicleId)
    }
}
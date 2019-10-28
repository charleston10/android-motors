package br.com.charleston.data.di

import br.com.charleston.data.cloud.requests.AppApi
import br.com.charleston.data.repository.AppDataRepository
import br.com.charleston.data.repository.store.VehicleStore
import br.com.charleston.data.repository.cloud.CloudDataStore
import br.com.charleston.data.repository.store.FavoriteStore
import br.com.charleston.data.repository.store.MakeStore
import br.com.charleston.domain.repository.IAppRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {


    @Provides
    fun cloudDataStore(
        api: AppApi
    ): CloudDataStore {
        return CloudDataStore(api)
    }

    @Provides
    fun dataRepository(
        cloud: CloudDataStore,
        vehicleStore: VehicleStore,
        favoriteStore: FavoriteStore,
        makeStore: MakeStore
    ): IAppRepository {
        return AppDataRepository(
            cloud = cloud,
            vehicleStore = vehicleStore,
            favoriteStore = favoriteStore,
            makeStore = makeStore
        )
    }
}
package br.com.charleston.data.di

import br.com.charleston.data.cloud.requests.AppApi
import br.com.charleston.data.repository.AppDataRepository
import br.com.charleston.data.repository.cache.LocalDataStore
import br.com.charleston.data.repository.cloud.CloudDataStore
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
        local: LocalDataStore
    ): IAppRepository {
        return AppDataRepository(cloud, local)
    }
}
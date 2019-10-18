package br.com.charleston.data.cloud

import br.com.charleston.data.cloud.requests.AppApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RequestModule {

    @Provides
    fun provideApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }
}
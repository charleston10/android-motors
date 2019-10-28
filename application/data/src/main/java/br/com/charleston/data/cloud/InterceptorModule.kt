package br.com.charleston.data.cloud

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.Module
import dagger.Provides
import okhttp3.CacheControl
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class InterceptorModule {

    @Provides
    @Singleton
    @Named(RESPONSE_INTERCEPTOR)
    fun provideInterceptorResponse(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json; charset=utf-8")
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    @Named(USER_AGENT_INTERCEPTOR)
    fun provideUserAgentInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader("User-Agent", userAgent())
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    @Named(CACHE_INTERCEPTOR)
    fun provideCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork(context)) {
                /*
                    *  If there is Internet, get the cache that was stored 5 seconds ago.
                    *  If the cache is older than 5 seconds, then discard it,
                    *  and indicate an error in fetching the response.
                    *  The 'max-age' attribute is responsible for this behavior.
                */
                request
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build()
            } else {
                request
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
                /*
                   *  If there is no Internet, get the cache that was stored 7 days ago.
                   *  If the cache is older than 7 days, then discard it,
                   *  and indicate an error in fetching the response.
                   *  The 'max-stale' attribute is responsible for this behavior.
                   *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
               */
            }

            chain.proceed(request)
        }
    }

    private fun userAgent(): String {
        val version = "1.0.0"
        val osVersion = android.os.Build.VERSION.RELEASE
        val pack = " br.com.charleston.motors"
        val device = android.os.Build.MODEL
        return "Charleston.Motors/$version ($pack; OS Version:$osVersion; Android $device)"
    }

    private fun hasNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnected
    }
}
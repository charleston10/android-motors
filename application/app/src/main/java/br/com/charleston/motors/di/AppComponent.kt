package br.com.charleston.motors.di

import android.app.Application
import br.com.charleston.core.modules.AndroidModule
import br.com.charleston.core.modules.FactoryModule
import br.com.charleston.data.cloud.InterceptorModule
import br.com.charleston.data.cloud.NetworkModule
import br.com.charleston.data.cloud.RequestModule
import br.com.charleston.data.cloud.UrlApiModule
import br.com.charleston.data.di.DataModule
import br.com.charleston.motors.AndroidApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FactoryModule::class,
        AndroidModule::class,
        ViewModelModule::class,
        InterceptorModule::class,
        NetworkModule::class,
        RequestModule::class,
        UrlApiModule::class,
        DataModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun moduleUrlApi(module: UrlApiModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: AndroidApplication)
}
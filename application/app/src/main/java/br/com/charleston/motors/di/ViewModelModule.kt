package br.com.charleston.motors.di

import androidx.lifecycle.ViewModel
import br.com.charleston.core.viewmodel.ViewModelKey
import br.com.charleston.motors.presentation.HomeViewModel
import br.com.charleston.motors.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel
}
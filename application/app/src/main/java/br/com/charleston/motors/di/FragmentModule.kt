package br.com.charleston.motors.di

import br.com.charleston.motors.presentation.screens.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun homeFragment() : HomeFragment
}
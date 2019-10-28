package br.com.charleston.motors.di

import br.com.charleston.motors.presentation.screens.home.HomeFragment
import br.com.charleston.motors.presentation.screens.vehicle.detail.VehicleDetailFragment
import br.com.charleston.motors.presentation.screens.vehicle.list.VehicleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun homeFragment() : HomeFragment

    @ContributesAndroidInjector
    abstract fun vehicleFragment() : VehicleListFragment

    @ContributesAndroidInjector
    abstract fun vehicleDetailFragment(): VehicleDetailFragment
}
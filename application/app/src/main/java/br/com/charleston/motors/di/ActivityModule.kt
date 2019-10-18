package br.com.charleston.motors.di

import br.com.charleston.core.base.BaseActivity
import br.com.charleston.motors.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}
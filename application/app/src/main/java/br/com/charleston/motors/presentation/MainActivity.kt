package br.com.charleston.motors.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ActivityMainBinding

import androidx.lifecycle.ViewModelProviders
import br.com.charleston.core.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observerViewModel()
        getViewModel().input.getMakes()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    private fun observerViewModel() {
        getViewModel().output.makeLiveData.observe(this, Observer {
            getViewDataBinding().makes = it.toTypedArray()
        })
    }
}
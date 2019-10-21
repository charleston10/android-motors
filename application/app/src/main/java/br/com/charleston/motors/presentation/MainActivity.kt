package br.com.charleston.motors.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ActivityMainBinding

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.core.base.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observerViewModel()
//        setupScroll()
        getViewModel().input.initialize()
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

        getViewModel().output.vehicleLiveData.observe(this, Observer {
            getViewDataBinding().vehicles = it.toTypedArray()
        })

        getViewModel().output.avatarLiveData.observe(this, Observer {
            getViewDataBinding().avatar = it
        })

        getViewModel().output.favoriteEvent.observe(this, Observer {
            handlerState(it)
        })
    }

    private fun handlerState(favoriteState: FavoriteState) {
        when (favoriteState) {
            is FavoriteState.Empty -> {
                getViewDataBinding().includeContainerVehicle.isEmpty = true
            }
            is FavoriteState.Success -> {
                getViewDataBinding().includeContainerVehicle.isEmpty = false
            }
        }
    }

    /* private fun setupScroll() {
         val list = getViewDataBinding().listVehicles

         list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
             override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                 val visiblePosition =
                     (list.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                 if (visiblePosition == (list.adapter as VehicleAdapter).itemCount - 1) {
                     getViewModel().input.nextVehiclePage()
                 }
             }
         })
     }*/
}
package br.com.charleston.motors.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        getViewModel().input.initialize()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
    }

    private fun observerViewModel() {
        getViewModel().output.makeLiveData.observe(this, Observer {
            getViewDataBinding().makes = it.toTypedArray()
        })

        getViewModel().output.vehicleLiveData.observe(this, Observer {
            getViewDataBinding().vehicles = it.toTypedArray()
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
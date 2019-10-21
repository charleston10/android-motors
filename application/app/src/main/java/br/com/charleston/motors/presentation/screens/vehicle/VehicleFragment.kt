package br.com.charleston.motors.presentation.screens.vehicle

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentVehicleBinding
import br.com.charleston.motors.presentation.adapters.FavoriteAdapter

class VehicleFragment : BaseFragment<FragmentVehicleBinding, VehicleViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        getViewModel().input.findAllVehicles()
        setupScroll()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_vehicle
    }

    override fun getViewModel(): VehicleViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(VehicleViewModel::class.java)
    }

    private fun observerViewModel(){
        getViewModel().output.run {
            vehicleEvent.observe(this@VehicleFragment,
                Observer {
                    handlerState(it)
                })


        }
    }

    private fun handlerState(state: VehicleState){
        when(state){
            is VehicleState.Empty -> {
                getViewDataBinding().isEmpty = true
                getViewDataBinding().isLoading = false
                getViewDataBinding().isError = false
            }

            is VehicleState.Loading -> {
                getViewDataBinding().isEmpty = false
                getViewDataBinding().isLoading = true
                getViewDataBinding().isError = false
            }

            is VehicleState.Error -> {
                getViewDataBinding().isEmpty = false
                getViewDataBinding().isLoading = false
                getViewDataBinding().isError = true
            }

            is VehicleState.Success -> {
                getViewDataBinding().isEmpty = false
                getViewDataBinding().isLoading = false
                getViewDataBinding().isError = false
            }
        }
    }

    private fun setupScroll() {
        val list = getViewDataBinding().listVehicles

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visiblePosition =
                    (list.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                if (visiblePosition == (list.adapter as FavoriteAdapter).itemCount - 1) {
                    getViewModel().input.nextVehiclePage()
                }
            }
        })
    }
}
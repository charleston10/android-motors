package br.com.charleston.motors.presentation.screens.vehicle.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentVehicleBinding
import br.com.charleston.motors.presentation.adapters.VehicleAdapter

class VehicleFragment : BaseFragment<FragmentVehicleBinding, VehicleViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        bindView()
        setupScroll()
        findVehicles()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_vehicle
    }

    override fun getViewModel(): VehicleViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(VehicleViewModel::class.java)
    }

    private fun observerViewModel() {
        getViewModel().output.run {
            vehicleEvent.observe(this@VehicleFragment,
                Observer {
                    handlerState(it)
                })

            vehicleListLiveData.observe(this@VehicleFragment,
                Observer {
                    getViewDataBinding().vehicles = it.toTypedArray()
                })
        }
    }

    private fun handlerState(state: VehicleState) {
        when (state) {
            is VehicleState.Empty -> {
                getViewDataBinding().isEmpty = true
                getViewDataBinding().isLoading = false
                getViewDataBinding().isError = false
                getViewDataBinding().isLoadingPage = false
            }

            is VehicleState.Loading -> {
                getViewDataBinding().isEmpty = false
                getViewDataBinding().isLoading = true
                getViewDataBinding().isError = false
                getViewDataBinding().isLoadingPage = false
            }

            is VehicleState.Error -> {
                getViewDataBinding().isEmpty = false
                getViewDataBinding().isLoading = false
                getViewDataBinding().isError = true
                getViewDataBinding().isLoadingPage = false
            }

            is VehicleState.Success -> {
                getViewDataBinding().isEmpty = false
                getViewDataBinding().isLoading = false
                getViewDataBinding().isError = false
                getViewDataBinding().isLoadingPage = false
            }

            is VehicleState.LoadingPage -> {
                getViewDataBinding().isEmpty = false
                getViewDataBinding().isLoading = false
                getViewDataBinding().isError = false
                getViewDataBinding().isLoadingPage = true
            }

            is VehicleState.StartDetail -> {
                startDetail(state.model)
            }
        }
    }

    private fun startDetail(model: VehicleModel) {
        view?.let {
            val action = VehicleFragmentDirections
                .actionVehicleFragmentToVehicleDetailFragment(model)

            Navigation
                .findNavController(it)
                .navigate(action)
        }
    }

    private fun bindView() {
        getViewDataBinding().viewModel = getViewModel()
    }

    private fun setupScroll() {
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
    }

    private fun findVehicles() {
        arguments?.let {
            val safeArgs = VehicleFragmentArgs.fromBundle(it)
            val model = safeArgs.makeModel
            getViewDataBinding().make = model
            getViewModel().input.findVehicles(model)
        }
    }
}
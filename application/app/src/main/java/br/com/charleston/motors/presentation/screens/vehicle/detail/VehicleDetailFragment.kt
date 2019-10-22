package br.com.charleston.motors.presentation.screens.vehicle.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentVehicleDetailBinding

class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding, VehicleDetailViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindModel()
        observerViewModel()
        bindView()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_vehicle_detail
    }

    override fun getViewModel(): VehicleDetailViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(VehicleDetailViewModel::class.java)
    }

    private fun bindModel() {
        arguments?.let {
            val safeArgs = VehicleDetailFragmentArgs.fromBundle(it)
            val model = safeArgs.vehicleModel
            getViewDataBinding().model = model
        }
    }

    private fun observerViewModel() {
        getViewModel().output.vehicleEvent.observe(this,
            Observer {
                handlerState(it)
            })
    }

    private fun handlerState(state: VehicleDetailState) {
        when (state) {
            is VehicleDetailState.Loading -> {
                showLoading(true)
            }

            is VehicleDetailState.Success -> {
                showMessageSuccess()
                showLoading(false)
            }

            is VehicleDetailState.Error -> {
                showMessageError()
                showLoading(false)
            }
        }
    }

    private fun showMessageError() {
        Toast.makeText(this.context, "Error in favoring", Toast.LENGTH_SHORT).show()
    }

    private fun showMessageSuccess() {
        Toast.makeText(this.context, "Success favorite", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(value: Boolean) {
        getViewDataBinding().isLoading = value
    }

    private fun bindView(){
        getViewDataBinding().viewModel = getViewModel()
    }
}
package br.com.charleston.motors.presentation.screens.vehicle.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentVehicleDetailBinding

class VehicleDetailFragment : BaseFragment<FragmentVehicleDetailBinding, BaseViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_vehicle_detail
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(BaseViewModel::class.java)
    }

    private fun bindModel() {
        arguments?.let {
            val safeArgs = VehicleDetailFragmentArgs.fromBundle(it)
            val model = safeArgs.vehicleModel
            getViewDataBinding().model = model
        }
    }
}
package br.com.charleston.motors.presentation.screens.vehicle

import androidx.lifecycle.ViewModelProviders
import br.com.charleston.core.base.BaseFragment
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.FragmentVehicleBinding

class VehicleFragment : BaseFragment<FragmentVehicleBinding, BaseViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_vehicle
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(BaseViewModel::class.java)
    }
}
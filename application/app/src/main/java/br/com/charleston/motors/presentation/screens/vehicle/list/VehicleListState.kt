package br.com.charleston.motors.presentation.screens.vehicle.list

import android.widget.ImageView
import br.com.charleston.domain.model.VehicleModel

sealed class VehicleListState {
    object Empty : VehicleListState()
    object Loading : VehicleListState()
    object LoadingPage : VehicleListState()
    object Success : VehicleListState()
    object Error : VehicleListState()

    class StartDetail(val carImageView: ImageView, val model: VehicleModel) : VehicleListState()
}
package br.com.charleston.motors.presentation.screens.vehicle.list

import br.com.charleston.domain.model.VehicleModel

sealed class VehicleState {
    object Empty : VehicleState()
    object Loading : VehicleState()
    object LoadingPage : VehicleState()
    object Success : VehicleState()
    object Error : VehicleState()

    class StartDetail(val model: VehicleModel) : VehicleState()
}
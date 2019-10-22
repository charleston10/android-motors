package br.com.charleston.motors.presentation.screens.vehicle.detail

sealed class VehicleDetailState {
    object Loading : VehicleDetailState()
    object Success : VehicleDetailState()
    object Error : VehicleDetailState()
}
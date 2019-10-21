package br.com.charleston.motors.presentation.screens.vehicle

sealed class VehicleState {
    object Empty : VehicleState()
    object Loading : VehicleState()
    object Success : VehicleState()
    object Error : VehicleState()
}
package br.com.charleston.motors.presentation.screens.vehicle

sealed class VehicleState {
    object Empty : VehicleState()
    object Loading : VehicleState()
    object LoadingPage : VehicleState()
    object Success : VehicleState()
    object Error : VehicleState()
}
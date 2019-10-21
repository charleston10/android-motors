package br.com.charleston.motors.presentation.screens.home

sealed class FavoriteState {
    object Empty : FavoriteState()
    object Success : FavoriteState()
}
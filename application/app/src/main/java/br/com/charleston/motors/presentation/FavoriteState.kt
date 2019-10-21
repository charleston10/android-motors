package br.com.charleston.motors.presentation

sealed class FavoriteState {
    object Empty : FavoriteState()
    object Success : FavoriteState()
}
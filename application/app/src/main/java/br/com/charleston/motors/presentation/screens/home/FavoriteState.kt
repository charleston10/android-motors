package br.com.charleston.motors.presentation.screens.home

import android.view.View
import android.widget.ImageView
import br.com.charleston.domain.model.VehicleModel

sealed class FavoriteState {
    object Empty : FavoriteState()
    object Success : FavoriteState()

    class Remove(
        val anchor: View,
        val vehicleModel: VehicleModel,
        val position: Int
    ) : FavoriteState()

    class Removed(val position: Int, val vehicleModel: VehicleModel) : FavoriteState()
    object RemoveFail : FavoriteState()
    class StartDetail(val carImageView: ImageView, val vehicleModel: VehicleModel) : FavoriteState()

    object FilterNoResult : FavoriteState()
    class FilterSuccess(val list: List<VehicleModel>) : FavoriteState()
}
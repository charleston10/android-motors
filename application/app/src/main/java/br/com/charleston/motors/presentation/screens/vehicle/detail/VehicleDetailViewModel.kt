package br.com.charleston.motors.presentation.screens.vehicle.detail

import br.com.charleston.core.ActionLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.SaveFavoriteUseCase
import br.com.charleston.domain.model.VehicleModel
import javax.inject.Inject

interface InputVehicleDetailViewModel {
    fun favorite(vehicleModel: VehicleModel)
}

interface OutputVehicleDetailViewModel {
    val vehicleEvent: ActionLiveData<VehicleDetailState>
}

interface ContractVehicleDetailViewModel {
    val input: InputVehicleDetailViewModel
    val output: OutputVehicleDetailViewModel
}

class VehicleDetailViewModel @Inject constructor(
    private val saveFavoriteUseCase: SaveFavoriteUseCase
) : BaseViewModel(),
    ContractVehicleDetailViewModel,
    InputVehicleDetailViewModel,
    OutputVehicleDetailViewModel {

    override val input: InputVehicleDetailViewModel get() = this
    override val output: OutputVehicleDetailViewModel get() = this

    private val vehicleObserverEvent = ActionLiveData<VehicleDetailState>()
    override val vehicleEvent: ActionLiveData<VehicleDetailState> get() = vehicleObserverEvent

    override fun favorite(vehicleModel: VehicleModel) {
        saveFavoriteUseCase.execute(object : DefaultObserver<Boolean>() {
            override fun onStart() {
                super.onStart()
                vehicleObserverEvent.postValue(VehicleDetailState.Loading)
            }

            override fun onNext(t: Boolean) {
                vehicleObserverEvent.postValue(VehicleDetailState.Success)
            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                exception.printStackTrace()
                vehicleObserverEvent.postValue(VehicleDetailState.Error)
            }
        }, vehicleModel.id)
    }
}
package br.com.charleston.motors.presentation.screens.vehicle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.charleston.core.ActionLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.GetVehicleUseCase
import br.com.charleston.domain.model.VehicleModel
import javax.inject.Inject

interface InputVehicleViewModel {
    fun findAllVehicles()
    fun nextVehiclePage()
}

interface OutputVehicleViewModel {
    val vehicleEvent: ActionLiveData<VehicleState>
    val vehicleListLiveData: LiveData<List<VehicleModel>>
}

interface ContractVehicleViewModel {
    val input: InputVehicleViewModel
    val output: OutputVehicleViewModel
}

class VehicleViewModel @Inject constructor(
    private val getVehicleUseCase: GetVehicleUseCase
) : BaseViewModel(),
    ContractVehicleViewModel,
    InputVehicleViewModel,
    OutputVehicleViewModel {

    private var vehiclePage = 1
    private var perPage = 10
    private var breakPagination = false

    override val input: InputVehicleViewModel get() = this
    override val output: OutputVehicleViewModel get() = this

    private val vehicleObserverEvent = ActionLiveData<VehicleState>()
    override val vehicleEvent: ActionLiveData<VehicleState> get() = vehicleObserverEvent

    private val vehicleListMutableLiveData = MutableLiveData<List<VehicleModel>>()
    override val vehicleListLiveData: LiveData<List<VehicleModel>> get() = vehicleListMutableLiveData

    override fun findAllVehicles() {
        getVehicles()
    }

    override fun nextVehiclePage() {
        if (!breakPagination) {
            vehiclePage++
            getVehicles()
        }
    }

    private fun getVehicles() {
        getVehicleUseCase.execute(object : DefaultObserver<List<VehicleModel>>() {
            override fun onStart() {
                super.onStart()
                vehicleObserverEvent.postValue(VehicleState.Loading)
            }

            override fun onNext(t: List<VehicleModel>) {
                handlerSuccess(t)
            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                vehicleObserverEvent.postValue(VehicleState.Error)
            }
        }, vehiclePage)
    }

    private fun handlerSuccess(items: List<VehicleModel>) {
        if (items.isEmpty()) {
            vehicleObserverEvent.postValue(VehicleState.Empty)
        } else {
            vehicleObserverEvent.postValue(VehicleState.Success)
            vehicleListMutableLiveData.postValue(items)
            treatPage(items.size)
        }
    }

    private fun treatPage(total: Int) {
        if (total < perPage) {
            breakPagination = true
        }
    }
}
package br.com.charleston.motors.presentation.screens.vehicle.list

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.charleston.core.ActionLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.GetVehicleUseCase
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import javax.inject.Inject

interface InputVehicleListViewModel {
    fun findVehicles(makeModel: MakeModel)
    fun nextVehiclePage()
    fun onSelectVehicle(carImageView: ImageView, vehicleModel: VehicleModel)
}

interface OutputVehicleListViewModel {
    val vehicleEvent: ActionLiveData<VehicleListState>
    val vehicleListLiveData: LiveData<List<VehicleModel>>
}

interface ContractVehicleListViewModel {
    val input: InputVehicleListViewModel
    val output: OutputVehicleListViewModel
}

class VehicleListViewModel @Inject constructor(
    private val getVehicleUseCase: GetVehicleUseCase
) : BaseViewModel(),
    ContractVehicleListViewModel,
    InputVehicleListViewModel,
    OutputVehicleListViewModel {

    private var initialized = false
    private var vehiclePage = 1
    private var perPage = 10
    private var breakPagination = false
    private var makeModel: MakeModel? = null

    override val input: InputVehicleListViewModel get() = this
    override val output: OutputVehicleListViewModel get() = this

    private val vehicleObserverEvent = ActionLiveData<VehicleListState>()
    override val vehicleEvent: ActionLiveData<VehicleListState> get() = vehicleObserverEvent

    private val vehicleListMutableLiveData = MutableLiveData<List<VehicleModel>>()
    override val vehicleListLiveData: LiveData<List<VehicleModel>> get() = vehicleListMutableLiveData

    override fun findVehicles(makeModel: MakeModel) {
        if (!initialized) {
            this.makeModel = makeModel
            getVehicles(makeModel)
            initialized = true
        }
    }

    override fun nextVehiclePage() {
        if (!breakPagination && makeModel != null
            && (vehicleObserverEvent.value != null && vehicleObserverEvent.value != VehicleListState.LoadingPage)
        ) {
            vehiclePage++
            getVehicles(makeModel!!)
        }
    }

    override fun onSelectVehicle(carImageView: ImageView, vehicleModel: VehicleModel) {
        vehicleObserverEvent.postValue(
            VehicleListState.StartDetail(
                carImageView,
                vehicleModel
            )
        )
    }

    private fun getVehicles(makeModel: MakeModel) {
        getVehicleUseCase.execute(object : DefaultObserver<List<VehicleModel>>() {
            override fun onStart() {
                super.onStart()
                vehicleListMutableLiveData.value?.size?.let {
                    if (it == 0) {
                        showContentLoading()
                    } else {
                        showLoadingMoreItems()
                    }
                } ?: showContentLoading()
            }

            override fun onNext(t: List<VehicleModel>) {
                handlerSuccess(t)
            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                exception.printStackTrace()
                vehicleObserverEvent.postValue(VehicleListState.Error)
            }
        }, Pair(vehiclePage, makeModel))
    }

    private fun handlerSuccess(items: List<VehicleModel>) {
        if (items.isEmpty()) {
            vehicleObserverEvent.postValue(VehicleListState.Empty)
        } else {
            vehicleObserverEvent.postValue(VehicleListState.Success)
            vehicleListMutableLiveData.postValue(items)
            treatPage(items.size)
        }
    }

    private fun treatPage(total: Int) {
        if (total < perPage) {
            breakPagination = true
        }
    }

    private fun showContentLoading() {
        vehicleObserverEvent.postValue(VehicleListState.Loading)
    }

    private fun showLoadingMoreItems() {
        vehicleObserverEvent.postValue(VehicleListState.LoadingPage)
    }
}
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
import java.util.*
import javax.inject.Inject

interface InputVehicleViewModel {
    fun findVehicles(makeModel: MakeModel)
    fun nextVehiclePage()
    fun onSelectVehicle(carImageView: ImageView, vehicleModel: VehicleModel)
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
    private val list: ArrayList<VehicleModel> = arrayListOf()
    private var makeModel: MakeModel? = null

    override val input: InputVehicleViewModel get() = this
    override val output: OutputVehicleViewModel get() = this

    private val vehicleObserverEvent = ActionLiveData<VehicleState>()
    override val vehicleEvent: ActionLiveData<VehicleState> get() = vehicleObserverEvent

    private val vehicleListMutableLiveData = MutableLiveData<List<VehicleModel>>()
    override val vehicleListLiveData: LiveData<List<VehicleModel>> get() = vehicleListMutableLiveData

    override fun findVehicles(makeModel: MakeModel) {
        this.makeModel = makeModel
        getVehicles(makeModel)
    }

    override fun nextVehiclePage() {
        if (!breakPagination && makeModel != null) {
            vehiclePage++
            getVehicles(makeModel!!)
        }
    }

    override fun onSelectVehicle(carImageView: ImageView, vehicleModel: VehicleModel) {
        vehicleObserverEvent.postValue(
            VehicleState.StartDetail(
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
                vehicleObserverEvent.postValue(VehicleState.Error)
            }
        }, Pair(vehiclePage, makeModel))
    }

    private fun handlerSuccess(items: List<VehicleModel>) {
        if (items.isEmpty()) {
            vehicleObserverEvent.postValue(VehicleState.Empty)
        } else {
            if (!breakPagination) list.addAll(items)

            vehicleObserverEvent.postValue(VehicleState.Success)
            vehicleListMutableLiveData.postValue(list)
            treatPage(items.size)
        }
    }

    private fun treatPage(total: Int) {
        if (total < perPage) {
            breakPagination = true
        }
    }

    private fun showContentLoading() {
        vehicleObserverEvent.postValue(VehicleState.Loading)
    }

    private fun showLoadingMoreItems() {
        vehicleObserverEvent.postValue(VehicleState.LoadingPage)
    }
}
package br.com.charleston.motors.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.GetMakeUseCase
import br.com.charleston.domain.interactor.GetVehicleUseCase
import br.com.charleston.domain.model.AvatarModel
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import javax.inject.Inject

interface InputMainViewModel {
    fun initialize()
    fun nextVehiclePage()
}

interface OutputMainViewModel {
    val makeLiveData: LiveData<List<MakeModel>>
    val vehicleLiveData: LiveData<List<VehicleModel>>
    val avatarLiveData: LiveData<AvatarModel>
}

interface ContractMainViewModel {
    val input: InputMainViewModel
    val output: OutputMainViewModel
}

class MainViewModel @Inject constructor(
    private val getMakeUseCase: GetMakeUseCase,
    private val getVehicleUseCase: GetVehicleUseCase
) : BaseViewModel(),
    ContractMainViewModel,
    InputMainViewModel,
    OutputMainViewModel {

    private var vehiclePage = 1
    private var perPage = 10
    private var breakPagination = false

    override val input: InputMainViewModel get() = this
    override val output: OutputMainViewModel get() = this

    private val makeMutableLiveData = MutableLiveData<List<MakeModel>>()
    override val makeLiveData: LiveData<List<MakeModel>> get() = makeMutableLiveData

    private val vehicleMutableLiveData = MutableLiveData<List<VehicleModel>>()
    override val vehicleLiveData: LiveData<List<VehicleModel>> get() = vehicleMutableLiveData

    private val avatarMutableLiveData = MutableLiveData<AvatarModel>()
    override val avatarLiveData: LiveData<AvatarModel> get() = avatarMutableLiveData

    override fun initialize() {
        getMakes()
        getVehicles()
        getAvatar()
    }

    override fun nextVehiclePage() {
        if (!breakPagination) {
            vehiclePage++
            getVehicles()
        }
    }

    private fun getAvatar() {
        avatarMutableLiveData.postValue(
            AvatarModel(
                name = "Charleston A.",
                url = "https://avatars3.githubusercontent.com/u/3097207?s=460&v=4"
            )
        )
    }

    private fun getMakes() {
        getMakeUseCase.execute(object : DefaultObserver<List<MakeModel>>() {
            override fun onNext(t: List<MakeModel>) {
                makeMutableLiveData.postValue(t)
            }
        })
    }

    private fun getVehicles() {
        getVehicleUseCase.execute(object : DefaultObserver<List<VehicleModel>>() {
            override fun onNext(t: List<VehicleModel>) {
                vehicleMutableLiveData.postValue(t)
                treatPage(t.size)
            }
        }, vehiclePage)
    }

    private fun treatPage(total: Int) {
        if (total < perPage) {
            breakPagination = true
        }
    }
}
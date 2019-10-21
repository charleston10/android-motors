package br.com.charleston.motors.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.charleston.core.ActionLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.GetFavoriteUseCase
import br.com.charleston.domain.interactor.GetMakeUseCase
import br.com.charleston.domain.interactor.GetVehicleUseCase
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import java.util.*
import javax.inject.Inject


interface InputHomeViewModel {
    fun initialize()
    fun nextVehiclePage()
}

interface OutputHomeViewModel {
    val makeLiveData: LiveData<List<MakeModel>>
    val vehicleLiveData: LiveData<List<VehicleModel>>

    val favoriteEvent: ActionLiveData<FavoriteState>
}

interface ContractHomeViewModel {
    val input: InputHomeViewModel
    val output: OutputHomeViewModel
}

class HomeViewModel @Inject constructor(
    private val getMakeUseCase: GetMakeUseCase,
    private val getVehicleUseCase: GetVehicleUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : BaseViewModel(),
    ContractHomeViewModel,
    InputHomeViewModel,
    OutputHomeViewModel {

    private var vehiclePage = 1
    private var perPage = 10
    private var breakPagination = false

    override val input: InputHomeViewModel get() = this
    override val output: OutputHomeViewModel get() = this

    private val makeMutableLiveData = MutableLiveData<List<MakeModel>>()
    override val makeLiveData: LiveData<List<MakeModel>> get() = makeMutableLiveData

    private val vehicleMutableLiveData = MutableLiveData<List<VehicleModel>>()
    override val vehicleLiveData: LiveData<List<VehicleModel>> get() = vehicleMutableLiveData

    private val favoriteObserverEvent = ActionLiveData<FavoriteState>()
    override val favoriteEvent: ActionLiveData<FavoriteState> get() = favoriteObserverEvent

    override fun initialize() {
        getMakes()
        getFavorites()
    }

    override fun nextVehiclePage() {
        if (!breakPagination) {
            vehiclePage++
            getVehicles()
        }
    }

    private fun getMakes() {
        getMakeUseCase.execute(object : DefaultObserver<List<MakeModel>>() {
            override fun onNext(t: List<MakeModel>) {
                makeMutableLiveData.postValue(
                    ArrayList(t).apply {
                        add(0, createGenericMake())
                    }
                )
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

    private fun getFavorites() {
        getFavoriteUseCase.execute(object : DefaultObserver<List<VehicleModel>>() {
            override fun onNext(t: List<VehicleModel>) {
                handlerFavorite(t)
            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                exception.printStackTrace()
            }
        })
    }

    private fun treatPage(total: Int) {
        if (total < perPage) {
            breakPagination = true
        }
    }

    private fun handlerFavorite(items: List<VehicleModel>) {
        if (items.isEmpty()) {
            favoriteEvent.postValue(FavoriteState.Empty)
        } else {
            vehicleMutableLiveData.postValue(items)
            favoriteEvent.postValue(FavoriteState.Success)
        }
    }

    private fun createGenericMake(): MakeModel {
        return MakeModel(0, "See all")
    }
}
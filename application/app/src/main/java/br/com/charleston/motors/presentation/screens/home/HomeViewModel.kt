package br.com.charleston.motors.presentation.screens.home

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.charleston.core.ActionLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.*
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import java.util.*
import javax.inject.Inject


interface InputHomeViewModel {
    fun initialize()
    fun onSelectMake(makeModel: MakeModel)
    fun onSelectLongVehicle(anchor: View, vehicleModel: VehicleModel, position: Int)
    fun onSelectShortVehicle(carImageView: ImageView, vehicleModel: VehicleModel)
    fun removeFavorite(vehicleModel: VehicleModel, position: Int)
    fun filterFavorite(filter: String)
}

interface OutputHomeViewModel {
    val makeLiveData: LiveData<List<MakeModel>>
    val vehicleLiveData: LiveData<List<VehicleModel>>

    val makeSelectEvent: ActionLiveData<MakeModel>
    val favoriteEvent: ActionLiveData<FavoriteState>
}

interface ContractHomeViewModel {
    val input: InputHomeViewModel
    val output: OutputHomeViewModel
}

class HomeViewModel @Inject constructor(
    private val getMakeUseCase: GetMakeUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val filterFavoriteUseCase: FilterFavoriteUseCase
) : BaseViewModel(),
    ContractHomeViewModel,
    InputHomeViewModel,
    OutputHomeViewModel {

    override val input: InputHomeViewModel get() = this
    override val output: OutputHomeViewModel get() = this

    private val makeMutableLiveData = MutableLiveData<List<MakeModel>>()
    override val makeLiveData: LiveData<List<MakeModel>> get() = makeMutableLiveData

    private val vehicleMutableLiveData = MutableLiveData<List<VehicleModel>>()
    override val vehicleLiveData: LiveData<List<VehicleModel>> get() = vehicleMutableLiveData

    private val favoriteObserverEvent = ActionLiveData<FavoriteState>()
    override val favoriteEvent: ActionLiveData<FavoriteState> get() = favoriteObserverEvent

    private val makeSelectObserverEvent = ActionLiveData<MakeModel>()
    override val makeSelectEvent: ActionLiveData<MakeModel> get() = makeSelectObserverEvent

    override fun initialize() {
        getMakes()
        getFavorites()
    }

    override fun onSelectMake(makeModel: MakeModel) {
        makeSelectObserverEvent.postValue(makeModel)
    }

    override fun onSelectLongVehicle(anchor: View, vehicleModel: VehicleModel, position: Int) {
        favoriteEvent.postValue(FavoriteState.Remove(anchor, vehicleModel, position))
    }

    override fun onSelectShortVehicle(carImageView: ImageView, vehicleModel: VehicleModel) {
        favoriteEvent.postValue(FavoriteState.StartDetail(carImageView, vehicleModel))
    }

    override fun filterFavorite(filter: String) {
        vehicleMutableLiveData.value?.let { list ->
            filterFavoriteUseCase.execute(object : DefaultObserver<List<VehicleModel>>() {
                override fun onNext(t: List<VehicleModel>) {
                    if (t.isEmpty()) {
                        favoriteEvent.postValue(FavoriteState.FilterNoResult)
                    } else {
                        favoriteEvent.postValue(FavoriteState.FilterSuccess(t))
                    }
                }

                override fun onError(exception: Throwable) {
                    super.onError(exception)
                    exception.printStackTrace()
                }
            }, Pair(filter, list))
        }
    }

    override fun removeFavorite(vehicleModel: VehicleModel, position: Int) {
        removeFavoriteUseCase.execute(object : DefaultObserver<Boolean>() {
            override fun onNext(t: Boolean) {
                if (t) {
                    favoriteEvent.postValue(FavoriteState.Removed(position, vehicleModel))
                } else {
                    favoriteEvent.postValue(FavoriteState.RemoveFail)
                }
            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                exception.printStackTrace()
                favoriteEvent.postValue(FavoriteState.RemoveFail)
            }
        }, vehicleModel.id)
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
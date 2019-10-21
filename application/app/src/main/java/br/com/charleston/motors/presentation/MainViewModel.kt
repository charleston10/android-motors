package br.com.charleston.motors.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.charleston.core.ActionLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.core.base.Event
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.GetFavoriteUseCase
import br.com.charleston.domain.interactor.GetMakeUseCase
import br.com.charleston.domain.interactor.GetVehicleUseCase
import br.com.charleston.domain.model.AvatarModel
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import java.util.*
import javax.inject.Inject

interface InputMainViewModel {
    fun initialize()
}

interface OutputMainViewModel {
    val avatarLiveData: LiveData<AvatarModel>
}

interface ContractMainViewModel {
    val input: InputMainViewModel
    val output: OutputMainViewModel
}

class MainViewModel @Inject constructor() : BaseViewModel(),
    ContractMainViewModel,
    InputMainViewModel,
    OutputMainViewModel {

    override val input: InputMainViewModel get() = this
    override val output: OutputMainViewModel get() = this

    private val avatarMutableLiveData = MutableLiveData<AvatarModel>()
    override val avatarLiveData: LiveData<AvatarModel> get() = avatarMutableLiveData

    override fun initialize() {
        getAvatar()
    }

    private fun getAvatar() {
        avatarMutableLiveData.postValue(
            AvatarModel(
                name = "Charleston A.",
                url = "https://media.licdn.com/dms/image/C4D03AQG7sDzTWMEGXg/profile-displayphoto-shrink_200_200/0?e=1577318400&v=beta&t=LxThP2tGPP_VC0Pnsfp2P3v5zSiZZxcDVuQSbJJSB2U"
            )
        )
    }
}
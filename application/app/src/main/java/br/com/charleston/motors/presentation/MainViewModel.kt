package br.com.charleston.motors.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.charleston.core.base.BaseViewModel
import br.com.charleston.domain.DefaultObserver
import br.com.charleston.domain.interactor.GetMakeUseCase
import br.com.charleston.domain.model.MakeModel
import javax.inject.Inject

interface InputMainViewModel {
    fun getMakes()
}

interface OutputMainViewModel {
    val makeLiveData: LiveData<List<MakeModel>>
}

interface ContractMainViewModel {
    val input: InputMainViewModel
    val output: OutputMainViewModel
}

class MainViewModel @Inject constructor(
    private val getMakeUseCase: GetMakeUseCase
) : BaseViewModel(),
    ContractMainViewModel,
    InputMainViewModel,
    OutputMainViewModel {

    override val input: InputMainViewModel get() = this
    override val output: OutputMainViewModel get() = this

    private val makeMutableLiveData = MutableLiveData<List<MakeModel>>()
    override val makeLiveData: LiveData<List<MakeModel>> get() = makeMutableLiveData

    override fun getMakes() {
        getMakeUseCase.execute(object : DefaultObserver<List<MakeModel>>() {
            override fun onNext(t: List<MakeModel>) {
                makeMutableLiveData.postValue(t)
            }
        })
    }
}
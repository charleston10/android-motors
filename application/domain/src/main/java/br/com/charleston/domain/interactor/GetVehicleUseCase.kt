package br.com.charleston.domain.interactor

import br.com.charleston.domain.UseCase
import br.com.charleston.domain.executor.PostExecutionThread
import br.com.charleston.domain.executor.ThreadExecutor
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.domain.repository.IAppRepository
import io.reactivex.Observable
import javax.inject.Inject


class GetVehicleUseCase @Inject constructor(
    private val repository: IAppRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<List<VehicleModel>, Int>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Int?): Observable<List<VehicleModel>> {
        return if (params != null) {
            repository.getVehicles(params)
        } else {
            Observable.error<List<VehicleModel>>(Throwable())
        }
    }
}
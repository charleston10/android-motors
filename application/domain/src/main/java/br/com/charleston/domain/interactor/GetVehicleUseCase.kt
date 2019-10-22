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
) : UseCase<List<VehicleModel>, Pair<Int, MakeModel>>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Pair<Int, MakeModel>?): Observable<List<VehicleModel>> {
        return if (params != null) {
            repository.getVehicles(params.first)
                .flatMapIterable { it }
                .filter {
                    it.make == params.second.name
                }
                .toList()
                .toObservable()
        } else {
            Observable.error<List<VehicleModel>>(Throwable())
        }
    }
}
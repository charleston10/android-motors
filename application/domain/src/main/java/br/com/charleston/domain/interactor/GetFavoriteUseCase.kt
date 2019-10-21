package br.com.charleston.domain.interactor

import br.com.charleston.domain.UseCase
import br.com.charleston.domain.executor.PostExecutionThread
import br.com.charleston.domain.executor.ThreadExecutor
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.domain.repository.IAppRepository
import io.reactivex.Observable
import javax.inject.Inject


class GetFavoriteUseCase @Inject constructor(
    private val repository: IAppRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<List<VehicleModel>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Observable<List<VehicleModel>> {
        return repository.getFavorites()
    }
}
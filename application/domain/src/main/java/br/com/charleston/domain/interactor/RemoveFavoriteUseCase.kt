package br.com.charleston.domain.interactor

import br.com.charleston.domain.UseCase
import br.com.charleston.domain.executor.PostExecutionThread
import br.com.charleston.domain.executor.ThreadExecutor
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.domain.repository.IAppRepository
import io.reactivex.Observable
import javax.inject.Inject


class RemoveFavoriteUseCase @Inject constructor(
    private val repository: IAppRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Boolean, Int>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Int?): Observable<Boolean> {
        return if (params != null) {
            try {
                repository.removeFavorite(params)
                Observable.just(true)
            } catch (e: Exception) {
                Observable.just(false)
            }
        } else {
            Observable.error(Throwable())
        }
    }
}
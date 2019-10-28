package br.com.charleston.domain.interactor

import br.com.charleston.domain.UseCase
import br.com.charleston.domain.executor.PostExecutionThread
import br.com.charleston.domain.executor.ThreadExecutor
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.domain.repository.IAppRepository
import io.reactivex.Observable
import javax.inject.Inject


class SaveFavoriteUseCase @Inject constructor(
    private val repository: IAppRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<Boolean, VehicleModel>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: VehicleModel?): Observable<Boolean> {
        return if (params != null) {
            try {
                if (params.isFavorite.get()) {
                    repository.removeFavorite(params.id)
                    Observable.just(false)
                } else {
                    repository.favorite(params.id)
                    Observable.just(true)
                }
            } catch (e: Exception) {
                Observable.just(false)
            }
        } else {
            Observable.error(Throwable())
        }
    }
}
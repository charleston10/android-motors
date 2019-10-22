package br.com.charleston.domain.interactor

import android.annotation.SuppressLint
import br.com.charleston.domain.UseCase
import br.com.charleston.domain.executor.PostExecutionThread
import br.com.charleston.domain.executor.ThreadExecutor
import br.com.charleston.domain.model.MakeModel
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.domain.repository.IAppRepository
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject


class FilterFavoriteUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<List<VehicleModel>, Pair<String, List<VehicleModel>>>(
    threadExecutor,
    postExecutionThread
) {

    @SuppressLint("DefaultLocale")
    override fun buildUseCaseObservable(params: Pair<String, List<VehicleModel>>?): Observable<List<VehicleModel>> {
        return if (params != null) {
            val filter = params.first.toLowerCase()
            val list = params.second

            Observable.create { emitter: ObservableEmitter<List<VehicleModel>> ->
                val filtered = list.filter {
                    it.model.toLowerCase().contains(filter)
                            || it.make.toLowerCase().contains(filter)
                            || it.color.toLowerCase().contains(filter)
                }
                emitter.onNext(filtered)
            }
        } else {
            Observable.error<List<VehicleModel>>(Throwable())
        }
    }
}
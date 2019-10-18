package br.com.charleston.domain.repository

import br.com.charleston.domain.model.*
import io.reactivex.Observable

interface IAppRepository {
    fun getMakes(): Observable<List<MakeModel>>
    fun getModel(makeId: String): Observable<List<Model>>
    fun getVersion(modelId: String): Observable<List<VersionModel>>
    fun getVehicles(page: Int): Observable<List<VehicleModel>>
}
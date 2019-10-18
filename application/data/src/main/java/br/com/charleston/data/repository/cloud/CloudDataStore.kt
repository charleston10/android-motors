package br.com.charleston.data.repository.cloud

import br.com.charleston.data.cloud.requests.AppApi
import br.com.charleston.data.cloud.responses.MakeResponse
import br.com.charleston.data.cloud.responses.ModelResponse
import br.com.charleston.data.cloud.responses.VehicleResponse
import br.com.charleston.data.cloud.responses.VersionResponse
import io.reactivex.Observable

class CloudDataStore(private val api: AppApi) {

    fun getMakes(): Observable<List<MakeResponse>> {
        return api.getMakes()
    }

    fun getModel(makeId: String): Observable<List<ModelResponse>> {
        return api.getModels(makeId)
    }

    fun getVersion(modelId: String): Observable<List<VersionResponse>> {
        return api.getVersions(modelId)
    }

    fun getVehicles(page: Int): Observable<List<VehicleResponse>> {
        return api.getVehicles(page)
    }
}
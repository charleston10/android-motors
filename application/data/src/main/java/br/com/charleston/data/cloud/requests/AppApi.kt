package br.com.charleston.data.cloud.requests

import br.com.charleston.data.cloud.QUERY_MAKE_ID
import br.com.charleston.data.cloud.QUERY_MODEL_ID
import br.com.charleston.data.cloud.QUERY_PAGE
import br.com.charleston.data.cloud.responses.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {

    @GET("/api/OnlineChallenge/Make")
    fun getMakes(): Observable<List<MakeResponse>>

    @GET("/api/OnlineChallenge/Model")
    fun getModels(
        @Query(QUERY_MAKE_ID) makeId: String
    ): Observable<List<ModelResponse>>

    @GET("/api/OnlineChallenge/Version")
    fun getVersions(
        @Query(QUERY_MODEL_ID) modelId: String
    ): Observable<List<VersionResponse>>

    @GET("/api/OnlineChallenge/Vehicles")
    fun getVehicles(
        @Query(QUERY_PAGE) page: Int
    ): Observable<List<VehicleResponse>>
}

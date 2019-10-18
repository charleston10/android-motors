package br.com.charleston.data.cloud.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VersionResponse(
    @SerializedName("ModelID") val modelId: Int,
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String
) : Serializable
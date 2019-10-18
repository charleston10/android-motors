package br.com.charleston.data.cloud.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelResponse(
    @SerializedName("MakeID") val makeId: Int,
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String
) : Serializable
package br.com.charleston.data.cloud.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MakeResponse(
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String
) : Serializable
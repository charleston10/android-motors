package br.com.charleston.data.cloud.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VehicleResponse(
    @SerializedName("ID") val id: Int,
    @SerializedName("Make") val make: String,
    @SerializedName("Model") val model: String,
    @SerializedName("Version") val version: String,
    @SerializedName("Image") val image: String,
    @SerializedName("KM") val km: Int,
    @SerializedName("Price") val price: String,
    @SerializedName("YearModel") val yearModel: String,
    @SerializedName("YearFab") val yearFab: String,
    @SerializedName("Color") val color: String
) : Serializable
package br.com.charleston.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VehicleModel(
    val id: Int,
    val make: String,
    val model: String,
    val version: String,
    val image: String,
    val km: Int,
    val price: String,
    val yearModel: String,
    val yearFab: String,
    val color: String
) : Parcelable
package br.com.charleston.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MakeModel(
    val id: Int,
    val name: String
) : Parcelable
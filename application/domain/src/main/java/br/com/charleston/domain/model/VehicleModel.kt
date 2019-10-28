package br.com.charleston.domain.model

import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import kotlinx.android.parcel.IgnoredOnParcel
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
    val color: String,
    var favorite: Boolean
) : Parcelable {

    @IgnoredOnParcel
    var isFavorite: ObservableBoolean = ObservableBoolean()

    init {
        isFavorite.set(favorite)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VehicleModel

        if (id != other.id) return false
        if (make != other.make) return false
        if (model != other.model) return false
        if (version != other.version) return false
        if (image != other.image) return false
        if (km != other.km) return false
        if (price != other.price) return false
        if (yearModel != other.yearModel) return false
        if (yearFab != other.yearFab) return false
        if (color != other.color) return false
        if (favorite != other.favorite) return false
        if (isFavorite != other.isFavorite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + make.hashCode()
        result = 31 * result + model.hashCode()
        result = 31 * result + version.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + km
        result = 31 * result + price.hashCode()
        result = 31 * result + yearModel.hashCode()
        result = 31 * result + yearFab.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + favorite.hashCode()
        result = 31 * result + isFavorite.hashCode()
        return result
    }
}
package br.com.charleston.motors.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import br.com.charleston.core.base.BaseAdapter
import br.com.charleston.core.base.BaseViewHolder
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ItemFavoriteBinding

interface FavoriteAdapterListener {
    fun onFavoriteLongSelected(anchor: View, vehicleModel: VehicleModel, position: Int)
    fun onFavoriteShortSelected(carImageView: ImageView, vehicleModel: VehicleModel)
}

class FavoriteAdapter(
    private val listener: FavoriteAdapterListener
) : BaseAdapter<VehicleModel, FavoriteAdapter.FavoriteAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapterViewHolder {
        return FavoriteAdapterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favorite,
                parent,
                false
            )
        )
    }

    inner class FavoriteAdapterViewHolder(private val item: ItemFavoriteBinding) :
        BaseViewHolder<VehicleModel>(item.root) {
        override fun bind(model: VehicleModel) {
            item.run {
                this.model = model
                this.root.setOnLongClickListener {
                    listener.onFavoriteLongSelected(it, model, adapterPosition)
                    true
                }
                this.root.setOnClickListener {
                    listener.onFavoriteShortSelected(this.ivCar, model)
                }
                executePendingBindings()
            }
        }
    }
}
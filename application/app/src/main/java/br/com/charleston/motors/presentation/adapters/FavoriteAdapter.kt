package br.com.charleston.motors.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import br.com.charleston.core.base.BaseAdapter
import br.com.charleston.core.base.BaseViewHolder
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ItemFavoriteBinding

class FavoriteAdapter : BaseAdapter<VehicleModel, FavoriteAdapter.FavoriteAdapterViewHolder>() {

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
                executePendingBindings()
            }
        }
    }
}
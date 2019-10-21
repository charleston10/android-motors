package br.com.charleston.motors.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import br.com.charleston.core.base.BaseAdapter
import br.com.charleston.core.base.BaseViewHolder
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ItemVehicleBinding

class VehicleAdapter : BaseAdapter<VehicleModel, VehicleAdapter.VehicleAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleAdapterViewHolder {
        return VehicleAdapterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_vehicle,
                parent,
                false
            )
        )
    }

    inner class VehicleAdapterViewHolder(private val item: ItemVehicleBinding) :
        BaseViewHolder<VehicleModel>(item.root) {
        override fun bind(model: VehicleModel) {
            item.run {
                this.model = model
                executePendingBindings()
            }
        }
    }
}
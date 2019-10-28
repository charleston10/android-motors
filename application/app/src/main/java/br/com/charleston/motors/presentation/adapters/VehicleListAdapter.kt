package br.com.charleston.motors.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import br.com.charleston.core.base.BaseAdapter
import br.com.charleston.core.base.BaseViewHolder
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ItemVehicleBinding

interface VehicleAdapterListener {
    fun onVehicleSelect(carImageView: ImageView, vehicleModel: VehicleModel)
}

class VehicleListAdapter(
    private val listener: VehicleAdapterListener
) : BaseAdapter<VehicleModel, VehicleListAdapter.VehicleListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListViewHolder {
        return VehicleListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_vehicle,
                parent,
                false
            )
        )
    }

    inner class VehicleListViewHolder(private val item: ItemVehicleBinding) :
        BaseViewHolder<VehicleModel>(item.root) {
        override fun bind(model: VehicleModel) {
            item.run {
                this.model = model
                this.imageView.transitionName = model.id.toString()
                root.setOnClickListener {
                    listener.onVehicleSelect(this.imageView, model)
                }
                executePendingBindings()
            }
        }
    }
}
package br.com.charleston.motors.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.charleston.core.base.BaseViewHolder
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.motors.R
import br.com.charleston.motors.databinding.ItemVehicleBinding
import java.util.*

interface VehicleAdapterListener {
    fun onVehicleSelect(carImageView: ImageView, vehicleModel: VehicleModel)
}

class VehicleListAdapter(
    private val listener: VehicleAdapterListener
) : ListAdapter<VehicleModel, VehicleListAdapter.VehicleListViewHolder>(DiffVehicleCallback) {

    companion object {
        val DiffVehicleCallback = object : DiffUtil.ItemCallback<VehicleModel>() {
            override fun areItemsTheSame(oldItem: VehicleModel, newItem: VehicleModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VehicleModel, newItem: VehicleModel): Boolean {
                return oldItem == newItem
            }
        }
    }

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

    override fun onBindViewHolder(holder: VehicleListViewHolder, position: Int) {
        holder.bind(getItem(position))
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